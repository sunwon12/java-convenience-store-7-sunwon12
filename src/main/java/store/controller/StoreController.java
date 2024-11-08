package store.controller;

import store.model.product.StoreService;
import store.view.InputView;
import store.view.OutptView;

public class StoreController {

    private final InputView inputView;
    private final OutptView outptView;
    private final StoreService service;

    public StoreController(InputView inputView, OutptView outptView) {
        this.inputView = inputView;
        this.outptView = outptView;
        this.service = new StoreService();
    }

    public void process() {
        outptView.printWelcome();
        service.initiallizeStocks();
    }
}



