package store.model.sell;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import store.PromotionDate;
import store.model.ShoppingCart;
import store.model.product.ProductName;
import store.model.product.Quantity;
import store.model.product.ReleasedProduct;

public class Promotion {

    public Map<ProductName, Quantity> getFreePromotion(ShoppingCart shoppingCart,
                                                       PromotionDate promotionDate) {
        Map<ProductName, ReleasedProduct> products = shoppingCart.getProducts();
        // filter 조건 디버깅
        products.entrySet().forEach(entry -> {
            System.out.println("Product: " + entry.getKey().getName());
            System.out.println("IsPromotion: " + entry.getValue().promotionType().isPromotion());
            System.out.println("IsBetweenDate: " + promotionDate.isBetweenPromotionDate(
                    entry.getValue().promotionType(), DateTimes.now()));
        });

        return products.entrySet().stream()
                .filter(entry -> {
                    boolean isPromotion = entry.getValue().promotionType().isPromotion();
                    boolean isInPromotionPeriod = promotionDate.isBetweenPromotionDate(
                            entry.getValue().promotionType(),
                            DateTimes.now()
                    );
                    return isPromotion && isInPromotionPeriod;  // 두 조건 모두 만족해야 함
                })
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
