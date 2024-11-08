package store.controller;

import java.util.List;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.ReleasedProduct;
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
        order();
    }

    private void showStocks() {
        Stocks stocks = service.getPrintStocks();
        outputView.showStocks(stocks);
    }

    private void order() {
        List<OrderProductInfoRequest> orderProductInfoRequests = inputView.readProductNameAndCount();
        List<ReleasedProduct> products = service.putInShoppingCart(orderProductInfoRequests);
    }
}



