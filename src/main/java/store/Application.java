package store;

import store.controller.StoreController;
import store.view.InputView.InputView;
import store.view.SimpleValidator;
import store.view.outputView.OutputView;

public class Application {
    public static void main(String[] args) {
        StoreController storeController = new StoreController(new InputView(new SimpleValidator()),
                new OutputView());
        storeController.process();
    }
}
