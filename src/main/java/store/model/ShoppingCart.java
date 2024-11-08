package store.model;

import java.util.ArrayList;
import java.util.List;
import store.model.product.ReleasedProduct;

public class ShoppingCart {

    private List<ReleasedProduct> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public ShoppingCart(List<ReleasedProduct> products) {
        this.products = new ArrayList<>(products);
    }

    public List<ReleasedProduct> add(List<ReleasedProduct> products) {
        this.products.addAll(products);
        return products;
    }
}
