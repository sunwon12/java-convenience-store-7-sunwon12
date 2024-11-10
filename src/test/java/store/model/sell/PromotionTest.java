package store.model.sell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.ShoppingCart;
import store.model.product.Money;
import store.model.product.Product;
import store.model.product.ProductName;
import store.model.product.PromotionType;
import store.model.product.Quantity;
import store.model.product.ReleasedProduct;

class PromotionTest {

    @DisplayName("프로모션 받을 상품명과 금액을 반환하다")
    @Test
    void test1() {
        ProductName productName = new ProductName("콜라");
        ReleasedProduct releasedProduct = new ReleasedProduct(new Product(productName, new Money(1000)),
                new Quantity(5), new Quantity(10), PromotionType.TWO_PLUS_ONE);
        ShoppingCart shoppingCart = new ShoppingCart(Map.of(productName, releasedProduct));

        Promotion promotion = new Promotion();
        Map<ProductName, Quantity> freePromotion = promotion.getFreePromotion(shoppingCart);

        assertEquals(productName, freePromotion.keySet().iterator().next());
        assertEquals(new Quantity(1), freePromotion.values().iterator().next());
    }
}
