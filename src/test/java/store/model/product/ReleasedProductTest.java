package store.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReleasedProductTest {

    @DisplayName("수량을 빼다")
    @Test
    void test1() {
        ProductName productName = new ProductName("콜라");
        ReleasedProduct releasedProduct1= new ReleasedProduct(new Product(productName, new Money(1000)),
                new Quantity(10), new Quantity(10), PromotionType.TWO_PLUS_ONE);
        ReleasedProduct releasedProduct2 = new ReleasedProduct(new Product(productName, new Money(1000)),
                new Quantity(1), new Quantity(2), PromotionType.TWO_PLUS_ONE);

        ReleasedProduct subtract = releasedProduct1.subtract(releasedProduct2);

        assertEquals(new Quantity(9), subtract.promotionQuantity());
        assertEquals(new Quantity(8), subtract.normalQuantity());

    }

}
