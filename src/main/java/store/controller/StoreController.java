package store.controller;

import store.model.StoreService;
import store.view.InputView;
import store.view.OutptView;

public class StoreController {

    private final StoreService storeService;
    private final InputView inputView;
    private final OutptView outptView;

    public StoreController(StoreService storeService, InputView inputView, OutptView outptView) {
        this.storeService = storeService;
        this.inputView = inputView;
        this.outptView = outptView;
    }

    public void process() {
    }
}



