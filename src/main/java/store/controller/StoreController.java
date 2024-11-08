package store.controller;

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
    }

    private void showStocks() {
        Stocks stocks = service.getPrintStocks();
        outputView.showStocks(stocks);
    }
}



