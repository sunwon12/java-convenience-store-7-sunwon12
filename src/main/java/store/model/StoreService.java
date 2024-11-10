package store.model;


import java.util.List;
import java.util.Map;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.Money;
import store.model.product.ProductName;
import store.model.product.Quantity;
import store.model.product.ReleasedProduct;
import store.model.product.ReleasedProducts;
import store.model.product.Stocks;
import store.model.sell.Membership;
import store.model.sell.Receipt;

public class StoreService {

    private final Stocks stocks;
    private final ShoppingCart shoppingCart;
    private final Membership membership;
    private final Receipt receipt;

    public StoreService() {
        this.stocks = new Stocks();
        this.shoppingCart = new ShoppingCart();
        this.membership = new Membership();
        this.receipt = new Receipt();
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

    public Map<ProductName, ReleasedProduct> calculateNonPromotionQuantity() {
        return shoppingCart.calculateNonPromotionQuantity();
    }


    public boolean checkEnoughStock(ProductName productName, Quantity quantity) {
        return stocks.validateQuantity(productName, quantity);
    }

    public void subtractFromCart(Map.Entry<ProductName, ReleasedProduct> nonPromotions) {
        shoppingCart.subtract(nonPromotions);
    }

    public Money useMembership() {
        ReleasedProducts releasedProducts = shoppingCart.getReleasedProducts();
        Money totalMoney = releasedProducts.getTotalMoney();
        return membership.discount(totalMoney);
    }
}
