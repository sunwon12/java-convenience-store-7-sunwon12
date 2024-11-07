package store.model;

import java.util.List;
import java.util.Map;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.Stock;
import store.model.product.StockProduct;
import store.model.sell.SellingMachine;

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

    public ShoppingCart addInCart(List<OrderProductInfoRequest> requests) {
        return this.shoppingCart.addProducts(requests);
    }

    public Map<String, Integer> findMissingPromotions(ShoppingCart shoppingCart) {
        return shoppingCart.findMissingPromotions();
    }

    public ShoppingCart takeMissingPromotion(ShoppingCart shoppingCart, String input) {
        if (input.equals("Y")) {
            return shoppingCart.takeMissingPromotion();
        }
        return shoppingCart;
    }
}
