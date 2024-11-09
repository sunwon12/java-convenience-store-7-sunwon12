package store.model.product;


import java.util.List;
import java.util.Map;
import store.model.CustomFileReader;
import store.model.ShoppingCart;
import store.model.dto.OrderProductInfoRequest;

public class StoreService {

    private final Stocks stocks;
    private final ShoppingCart shoppingCart;

    public StoreService() {
        this.stocks = new Stocks();
        this.shoppingCart = new ShoppingCart();
    }

    public void initiallizeStocks() {
        stocks.initialize(new CustomFileReader());
    }

    public Stocks getPrintStocks() {
        return stocks;
    }

    public Map<ProductName, Quantity> putInShoppingCart(List<OrderProductInfoRequest> requests) {
        Map<ProductName, ReleasedProduct> products = stocks.selectProduct(requests);
        shoppingCart.add(products);
        return shoppingCart.checkMissingPromotion();
    }

    public void putMissingInShoppingCart(ProductName productName, Quantity quantity) {
        Map<ProductName, ReleasedProduct> releasedProducts = stocks.selectProduct(productName, quantity);
        shoppingCart.add(releasedProducts);
    }
}
