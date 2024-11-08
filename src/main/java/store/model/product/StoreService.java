package store.model.product;


import java.util.List;
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

    public List<ReleasedProduct> putInShoppingCart(List<OrderProductInfoRequest> requests) {
        List<ReleasedProduct> products = requests.stream()
                .map(request -> stocks.selectProduct(new ProductName(request.name()),
                        new Quantity(request.amount())))
                .toList();

        return shoppingCart.add(products);
    }
}
