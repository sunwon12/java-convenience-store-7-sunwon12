package store.model;

import java.util.ArrayList;
import java.util.List;
import store.model.product.Product;
import store.model.product.Stock;

public class ShoppingCart {

    private final Stock stock;
    private List<Product> products;

    public ShoppingCart(Stock stock) {
        this.stock = stock;
        this.products = new ArrayList<>();
    }

    public ShoppingCart(Stock stock, List<Product> products) {
        this.stock = stock;
        this.products = new ArrayList<>(products);
    }
}
