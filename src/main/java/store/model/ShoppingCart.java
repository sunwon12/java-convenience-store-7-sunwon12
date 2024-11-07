package store.model;

import java.util.ArrayList;
import java.util.List;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.Stock;
import store.model.product.StockProduct;

public class ShoppingCart {

    private final Stock stock;
    private List<StockProduct> products;

    public ShoppingCart(Stock stock) {
        this.stock = stock;
        this.products = new ArrayList<>();
    }

    public void addProducts(List<OrderProductInfoRequest> requests) {
        for (OrderProductInfoRequest request : requests) {
            List<StockProduct> stockProduct = stock.giveProduct(request.name(), request.amount());
            products.addAll(stockProduct);
        }
    }

    public List<StockProduct> getProducts() {
        return products;
    }
}
