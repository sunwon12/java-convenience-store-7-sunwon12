package store.model;


import java.util.List;
import java.util.Map;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.Money;
import store.model.product.ProductName;
import store.model.product.Quantity;
import store.model.product.ReleasedProduct;
import store.model.product.Stocks;
import store.model.sell.Membership;
import store.model.sell.Promotion;
import store.model.sell.Receipt;

public class StoreService {

    private final Stocks stocks;
    private ShoppingCart shoppingCart;
    private final Membership membership;
    private final Promotion promotion;

    public StoreService() {
        this.stocks = new Stocks();
        this.membership = new Membership();
        this.promotion = new Promotion();
    }

    public void initiallizeStocks() {
        stocks.initialize(new CustomFileReader());
    }

    public Stocks getPrintStocks() {
        return stocks;
    }

    public Map<ProductName, Quantity> putInShoppingCart(ShoppingCart shoppingCart,
                                                        List<OrderProductInfoRequest> requests) {
        this.shoppingCart = shoppingCart;
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
        Money nonPromotionTotalMoney = shoppingCart.getNonPromotionTotalMoney();
        return membership.discount(nonPromotionTotalMoney);
    }

    public Receipt finalPurchase(Money membershipDiscount) {
        Map<ProductName, ReleasedProduct> products = shoppingCart.getProducts();
        Map<ProductName, Quantity> freePromotion = promotion.getFreePromotion(shoppingCart);
        return Receipt.create(products, freePromotion, membershipDiscount);
    }
}
