package store.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReleasedProductsTest {

    @DisplayName("출고된 상품의 총 금액을 반환하다")
    @Test
    void test1() {
        Money expected = new Money(13000);
        ProductName productName = new ProductName("콜라");
        ReleasedProduct releasedProduct = new ReleasedProduct(new Product(productName, new Money(1000)),
                new Quantity(3), new Quantity(10), PromotionType.TWO_PLUS_ONE);
        ReleasedProducts releasedProducts = new ReleasedProducts(Map.of(productName, releasedProduct));

        Money totalMoney = releasedProducts.getTotalMoney();

        assertEquals(expected, totalMoney);
    }

}
