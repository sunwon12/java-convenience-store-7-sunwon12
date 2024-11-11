package store.model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import store.PromotionDate;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.Money;
import store.model.product.ProductName;
import store.model.product.PromotionType;
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
    private final PromotionDate promotionDate;
    private final CustomFileReader customFileReader;

    public StoreService() {
        this.stocks = new Stocks();
        this.membership = new Membership();
        this.promotion = new Promotion();
        this.promotionDate = new PromotionDate();
        this.customFileReader = new CustomFileReader();
    }

    public void initiallizeStocks() {
        stocks.initiallize(customFileReader);
    }

    public void initiallizePromotionDate() {
        promotionDate.initallize(customFileReader);
    }

    public Stocks getPrintStocks() {
        return stocks;
    }

    public Map<ProductName, ReleasedProduct> putInShoppingCart(ShoppingCart shoppingCart,
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

    public boolean checkEnoughPromotionStock(ProductName productName, Quantity quantity) {
        return stocks.checkEnoughPromotionQuantity(productName, quantity);
    }

    public void subtractFromCart(Map.Entry<ProductName, ReleasedProduct> nonPromotions) {
        shoppingCart.subtract(nonPromotions);
        stocks.addReleasedProductInStocks(nonPromotions);
    }

    public Money useMembership() {
        Money nonPromotionTotalMoney = shoppingCart.getNonPromotionTotalMoney();
        return membership.discount(nonPromotionTotalMoney);
    }

    public Receipt finalPurchase(Money membershipDiscount) {
        Map<ProductName, ReleasedProduct> products = shoppingCart.getProducts();
        Map<ProductName, Quantity> freePromotion = promotion.getFreePromotion(shoppingCart, promotionDate);
        return Receipt.create(products, freePromotion, membershipDiscount);
    }

    public boolean isBetweenPromotionDate(PromotionType promotionType,
                                          LocalDateTime input) {
        return promotionDate.isBetweenPromotionDate(promotionType, input);
    }
}
