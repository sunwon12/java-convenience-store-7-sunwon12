package store.model.sell;

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

    public Receipt(Map<ProductName, ReleasedProduct> products,
                   Money totalPrice,
                   Map<ProductName, Quantity> promotionDiscountQuantity,
                   Money membershipDiscountPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
        this.promotionDiscountPrice = promotionDiscountQuantity;
        this.membershipDiscountPrice = membershipDiscountPrice;
    }
}
