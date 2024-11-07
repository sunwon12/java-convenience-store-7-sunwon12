package store.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.Stock;
import store.model.product.Product;

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

    public ShoppingCart addProducts(List<OrderProductInfoRequest> requests) {
        List<Product> products = new ArrayList<>();
        for (OrderProductInfoRequest request : requests) {
            List<Product> product = stock.giveProduct(request.name(), request.amount());
            products.addAll(product);
        }
        return new ShoppingCart(stock, products);
    }

    public Map<String,Integer> findMissingPromotions() {
        Map<String, Integer> map = new HashMap<>();
        for (Product product : products) {
            int i = product.countMissingPromotionCount();
            map.put(product.getName(), i);
        }

        return map;
    }

    // TODO
    // 프로모션 가져오려고 하는데 프로모션 없을 경우
    public ShoppingCart takeMissingPromotion() {
        for (Product product : products) {
            int count = product.countMissingPromotionCount();
            if (count >= 1) {
                product.plus(stock.giveProduct(product.getName(), count).getFirst());
            }
        }
        return new ShoppingCart(stock, products);
    }

    public List<Product> getProducts() {
        return products;
    }
}
