package store.controller;

import java.util.List;
import java.util.Map;
import store.model.ShoppingCart;
import store.model.StoreService;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.Product;
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
        ShoppingCart cart = order();
        ShoppingCart cartWithMissing = takeMissingPromotion(cart);
    }

    private ShoppingCart order() {
        outptView.printWelcome();

        List<Product> products = storeService.initializeStock();
        outptView.printProductInfo(products);

        List<OrderProductInfoRequest> requests = inputView.readProductNameAndCount();
        return storeService.addInCart(requests);
    }

    private ShoppingCart takeMissingPromotion(ShoppingCart shoppingCart) {
        Map<String, Integer> missingPromotion = storeService.findMissingPromotions(shoppingCart);
        for (Map.Entry<String, Integer> entry : missingPromotion.entrySet()) {
            String input = inputView.readMissingPromotionResponse(entry.getKey(), entry.getValue());
            shoppingCart = storeService.takeMissingPromotion(shoppingCart, input);
        }
        return shoppingCart;
    }

}



