package store.controller;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import store.model.product.ShoppingCart;
import store.model.service.StoreService;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.Money;
import store.model.product.ProductName;
import store.model.product.ReleasedProduct;
import store.model.product.Stocks;
import store.model.sell.Receipt;
import store.view.InputView.InputView;
import store.view.outputView.OutputView;


public class StoreController {

    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService service;

    public StoreController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.service = new StoreService();
    }

    public void process() {
        String response;
        service.initiallizeStocks();
        service.initiallizePromotionDate();
        do {
            attempt(() -> showStocks());
            attempt(() -> putInCart());
            attempt(() -> purchase());
            response = attempt(() -> inputView.readReorderResponse());
        } while (response.equals("Y"));
    }

    private void showStocks() {
        Stocks stocks = service.getPrintStocks();
        outputView.showStocks(stocks);
    }

    private void putInCart() {
        List<OrderProductInfoRequest> requests = inputView.readProductNameAndCount();
        Map<ProductName, ReleasedProduct> productNameQuantityMap = service.putInShoppingCart(new ShoppingCart(),
                requests);
        putMissingPromotion(productNameQuantityMap);
    }

    private void putMissingPromotion(Map<ProductName, ReleasedProduct> productNameQuantityMap) {
        productNameQuantityMap.entrySet().stream()
                .filter(entry -> checkPromotionAvailable(entry))
                .forEach(entry -> {
                    String response = inputView.readMissingPromotionResponse(entry.getKey().name(),
                            entry.getValue().promotionQuantity().getValue());
                    if (response.equals("Y")) {
                        service.putMissingInShoppingCart(entry.getKey(), entry.getValue().promotionQuantity());
                    }
                });
    }

    private boolean checkPromotionAvailable(Entry<ProductName, ReleasedProduct> entry) {
        return service.isBetweenPromotionDate(entry.getValue().promotionType(), DateTimes.now()) &&
                service.checkEnoughPromotionStock(entry.getKey(), entry.getValue().promotionQuantity());
    }

    private void purchase() {
        readPurchaseWithoutPromotion();
        Money membershipDiscountMoney = readUsingMembership();
        Receipt receipt = service.finalPurchase(membershipDiscountMoney);
        outputView.showReceipt(receipt);
    }

    private void readPurchaseWithoutPromotion() {
        Map<ProductName, ReleasedProduct> nonPromotions = service.calculateNonPromotionQuantity();
        for (Map.Entry<ProductName, ReleasedProduct> entry : nonPromotions.entrySet()) {
            if (service.isBetweenPromotionDate(entry.getValue().promotionType(), DateTimes.now())) {
                String response = inputView.readPurchaseWithoutPromotionResponse(
                        entry.getKey().name(),
                        entry.getValue().getTotalQuantity().getValue());
                subtractFromCart(entry, response);
            }
        }
    }

    private void subtractFromCart(Entry<ProductName, ReleasedProduct> entry, String response) {
        if (response.equals("N")) {
            service.subtractFromCart(entry);
        }
    }

    private Money readUsingMembership() {
        String response = inputView.readUsingMembershipResponse();
        Money membershipDiscountMoney = Money.ZERO;
        if (response.equals("Y")) {
            membershipDiscountMoney = service.useMembership();
        }
        return membershipDiscountMoney;
    }

    private <T> T attempt(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.showException(e.getMessage());
            return attempt(supplier);
        }
    }

    private void attempt(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException e) {
            outputView.showException(e.getMessage());
            attempt(runnable);
        }
    }
}



