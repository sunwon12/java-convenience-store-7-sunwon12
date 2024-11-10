package store.model.sell;

import java.util.Collections;
import java.util.Map;
import store.model.product.Money;
import store.model.product.ProductName;
import store.model.product.Quantity;
import store.model.product.ReleasedProduct;

public class Receipt {
    private final Map<ProductName, ReleasedProduct> products;
    private final Money totalPrice;
    private final Map<ProductName, Quantity> promotionDiscountPrice;
    private final Money membershipDiscountPrice;

    public Receipt(Map<ProductName, ReleasedProduct> products, Money totalPrice,
                   Map<ProductName, Quantity> promotionDiscountPrice, Money membershipDiscountPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
        this.promotionDiscountPrice = promotionDiscountPrice;
        this.membershipDiscountPrice = membershipDiscountPrice;
    }

    public Map<ProductName, ReleasedProduct> getProducts() {
        return Collections.unmodifiableMap(products);
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public Map<ProductName, Quantity> getPromotionDiscountPrice() {
        return Collections.unmodifiableMap(promotionDiscountPrice);
    }

    public Money getMembershipDiscountPrice() {
        return membershipDiscountPrice;
    }

    public static Receipt create(Map<ProductName, ReleasedProduct> products,
                                 Map<ProductName, Quantity> promotionQuantities,
                                 Money membershipDiscount) {
        Money total = calculateTotalPrice(products);
        return new Receipt(products, total, promotionQuantities, membershipDiscount);
    }

    private static Money calculateTotalPrice(Map<ProductName, ReleasedProduct> products) {
        return products.values().stream()
                .map(ReleasedProduct::getTotalMoney)
                .reduce(Money.ZERO, Money::add);
    }

    public Money getFinalPrice() {
        Money promotionDiscount = calculatePromotionDiscount();
        return totalPrice
                .subtract(promotionDiscount)
                .subtract(membershipDiscountPrice);
    }

    public Money calculatePromotionDiscount() {
        return promotionDiscountPrice.entrySet().stream()
                .map(entry -> {
                    ProductName productName = entry.getKey();
                    Quantity freeQuantity = entry.getValue();
                    Money price = products.get(productName).getPrice();

                    return price.multiply(freeQuantity.getValue());
                })
                .reduce(Money.ZERO, Money::add);
    }
}
