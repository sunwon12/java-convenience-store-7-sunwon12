package store.model;

import java.util.List;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.Stock;
import store.model.product.StockProduct;

public class StoreService {

    private final Stock stock;
    private final ShoppingCart shoppingCart;

    public StoreService() {
        this.stock = new Stock();
        this.shoppingCart = new ShoppingCart(this.stock);
    }

    public List<StockProduct> initializeStock() {
        stock.initializeStock();
        return stock.getStockProducts();
    }

    public void addInCart(List<OrderProductInfoRequest> requests) {
        shoppingCart.addProducts(requests);
    }
}
