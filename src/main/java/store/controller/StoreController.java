package store.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import store.model.ShoppingCart;
import store.model.StoreService;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.Money;
import store.model.product.ProductName;
import store.model.product.Quantity;
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
        do {
            showStocks();
            putInCart();
            purchase();
            response = attempt(() -> inputView.readReorderResponse());
        } while (response.equals("Y"));
    }

    private void showStocks() {
        Stocks stocks = service.getPrintStocks();
        outputView.showStocks(stocks);
    }

    private void putInCart() {
        List<OrderProductInfoRequest> requests = attempt(() -> inputView.readProductNameAndCount());
        Map<ProductName, Quantity> productNameQuantityMap = service.putInShoppingCart(new ShoppingCart(), requests);
        putMissingPromotion(productNameQuantityMap);
    }

    private void putMissingPromotion(Map<ProductName, Quantity> productNameQuantityMap) {
        productNameQuantityMap.entrySet().stream()
                .filter(entry -> service.checkEnoughStock(entry.getKey(), entry.getValue()))
                .forEach(entry -> {
                    String response = attempt(() -> inputView.readMissingPromotionResponse(
                            entry.getKey().getName(),
                            entry.getValue().getValue()));
                    if (response.equals("Y")) {
                        service.putMissingInShoppingCart(entry.getKey(), entry.getValue());
                    }
                });
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
            String response = attempt(() -> inputView.readPurchaseWithoutPromotionResponse(
                    entry.getKey().getName(),
                    entry.getValue().getTotalQuantity().getValue()));
            if (response.equals("N")) {
                service.subtractFromCart(entry);
            }
        }
    }

    private Money readUsingMembership() {
        String response = attempt(() -> inputView.readUsingMembershipResponse());
        Money membershipDiscountMoney = Money.ZERO;
        if (response.equals("Y")) {
            membershipDiscountMoney = service.useMembership();
        }
        return membershipDiscountMoney;
    }

    private <T> T attempt(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (IllegalArgumentException e) {
            outputView.showException(e.getMessage());
            return attempt(inputSupplier);
        }
    }
}



