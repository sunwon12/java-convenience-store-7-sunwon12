package store.model.sell;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import store.model.common.PromotionDate;
import store.model.product.ShoppingCart;
import store.model.product.ProductName;
import store.model.product.Quantity;
import store.model.product.ReleasedProduct;

public class Promotion {

    public Map<ProductName, Quantity> getFreePromotion(ShoppingCart shoppingCart,
                                                       PromotionDate promotionDate) {
        Map<ProductName, ReleasedProduct> products = shoppingCart.getProducts();
        return products.entrySet().stream()
                .filter(entry -> promotionDate.isBetweenPromotionDate(entry.getValue().promotionType(),
                        DateTimes.now()))
                .collect(Collectors.toMap(
                        Entry::getKey,
                        this::applyPromotion
                ));
    }

    private Quantity applyPromotion(Entry<ProductName, ReleasedProduct> entry) {
        ReleasedProduct product = entry.getValue();
        return product.getFreePromotionQuantity();
    }
}
