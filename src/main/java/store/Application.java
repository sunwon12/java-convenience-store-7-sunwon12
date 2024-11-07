package store;

import store.controller.StoreController;
import store.model.StoreService;
import store.view.InputView;
import store.view.OutptView;
import store.view.SimpleValidator;

public class Application {
    public static void main(String[] args) {
        StoreController storeController = new StoreController(new StoreService(), new InputView(new SimpleValidator()),
                new OutptView());
        storeController.process();
    }
}
