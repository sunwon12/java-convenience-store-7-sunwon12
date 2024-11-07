package store.model;

import store.model.product.Stock;

public class StoreService {

    private final Stock stock;
    private final ShoppingCart shoppingCart;

    public StoreService() {
        this.stock = new Stock();
        this.shoppingCart = new ShoppingCart(this.stock);
    }
}
