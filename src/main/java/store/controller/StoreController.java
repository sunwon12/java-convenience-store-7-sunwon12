package store.controller;

import java.util.List;
import java.util.Map;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.ProductName;
import store.model.product.Quantity;
import store.model.product.Stocks;
import store.model.product.StoreService;
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
        Map<ProductName, Quantity> productNameQuantityMap = service.putInShoppingCart(requests);
        for (Map.Entry<ProductName, Quantity> entry : productNameQuantityMap.entrySet()) {
             String answer = inputView.readMissingPromotionResponse(entry.getKey().getName(),
                    entry.getValue().getValue());
            if(answer.equals("Y")) {
                service.putMissingInShoppingCart(entry.getKey(), entry.getValue());
            }
        }
    }

    private void purchase() {
        readPurchaseWithoutPromotion();
    }

    private void readPurchaseWithoutPromotion() {
        Map<ProductName, Quantity> productNameQuantityMap = service.calculateNonPromotionQuantity();
        for (Map.Entry<ProductName, Quantity> entry : productNameQuantityMap.entrySet()) {
            ProductName productName = entry.getKey();
            Quantity quantity = entry.getValue();
            String nonPromotionalPurchaseResponse = inputView.readPurchaseWithoutPromotionResponse(productName.getName(), quantity.getValue());
        }
    }
}



