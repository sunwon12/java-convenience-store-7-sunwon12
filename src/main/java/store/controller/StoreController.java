package store.controller;

import java.util.List;
import java.util.Map;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.ProductName;
import store.model.product.Quantity;
import store.model.product.ReleasedProduct;
import store.model.product.Stocks;
import store.model.StoreService;
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
        service.initiallizeStocks();
        showStocks();
        putInCart();
        purchase();
    }

    private void showStocks() {
        Stocks stocks = service.getPrintStocks();
        outputView.showStocks(stocks);
    }

    private void putInCart() {
        List<OrderProductInfoRequest> requests = inputView.readProductNameAndCount();
        processNonPromotionalItems(service.putInShoppingCart(requests));
    }

    private void processNonPromotionalItems(Map<ProductName, Quantity> productNameQuantityMap) {
        productNameQuantityMap.entrySet().stream()
                .filter(entry -> service.checkEnoughStock(entry.getKey(), entry.getValue()))
                .forEach(entry -> {
                    String response = inputView.readMissingPromotionResponse(
                            entry.getKey().getName(),
                            entry.getValue().getValue());
                    if (response.equals("Y")) {
                        service.putMissingInShoppingCart(entry.getKey(), entry.getValue());
                    }
                });
    }

    private void purchase() {
        readPurchaseWithoutPromotion();
        readUsingMembership();
    }

    private void readPurchaseWithoutPromotion() {
        Map<ProductName, ReleasedProduct> nonPromotions = service.calculateNonPromotionQuantity();
        for (Map.Entry<ProductName, ReleasedProduct> entry : nonPromotions.entrySet()) {
            ProductName productName = entry.getKey();
            ReleasedProduct releasedProduct = entry.getValue();
            String response = inputView.readPurchaseWithoutPromotionResponse(productName.getName(), releasedProduct.getTotalQuantity().getValue());
            if(response.equals("N")) {
                service.subtractFromCart(entry);
            }
        }
    }

    private void readUsingMembership() {
        String response = inputView.readUsingMembershipResponse();
        if(response.equals("Y")) {
            service.useMembership();
        }

    }
}



