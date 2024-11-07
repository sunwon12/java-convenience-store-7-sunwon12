package store.controller;

import java.util.List;
import store.model.StoreService;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.StockProduct;
import store.view.InputView;
import store.view.OutptView;

public class StoreController {

    private StoreService storeService;
    private InputView inputView;
    private OutptView outptView;

    public StoreController(StoreService storeService, InputView inputView, OutptView outptView) {
        this.storeService = storeService;
        this.inputView = inputView;
        this.outptView = outptView;
    }

    public void process() {
        order();
    }

    private void order() {
        outptView.printWelcome();

        List<StockProduct> stockProducts = storeService.initializeStock();
        outptView.printProductInfo(stockProducts);

        List<OrderProductInfoRequest> requests = inputView.readProductNameAndCount();
        storeService.addInCart(requests);
    }
}


