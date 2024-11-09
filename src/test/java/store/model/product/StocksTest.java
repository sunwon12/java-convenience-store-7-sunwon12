package store.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.CustomFileReader;

class StocksTest {

    @DisplayName("존재하지 않는 상품을 꺼낼 시 예외를 반환한다")
    @Test
    void test1() {
        Stocks stocks = new Stocks();
        stocks.initialize(new CustomFileReader());

        assertThrows(IllegalArgumentException.class, () ->
                stocks.selectProduct(new ProductName("아무것도아니야"), new Quantity(1)));

    }

    @DisplayName("수량이 부족한 상품을 꺼낼 시 예외를 반환한다")
    @Test
    void test2() {
        Stocks stocks = new Stocks();
        stocks.initialize(new CustomFileReader());

        assertThrows(IllegalArgumentException.class, () ->
                stocks.selectProduct(new ProductName("콜라"), new Quantity(1000)));
    }

    @DisplayName("요청한 개수만큼 상품을 꺼낸다")
    @Test
    void test3() {
        Stocks stocks = new Stocks();
        stocks.initialize(new CustomFileReader());
        ProductName productName = new ProductName("콜라");

        Map<ProductName, ReleasedProduct> releasedProductMap = stocks.selectProduct(productName,
                new Quantity(5));
        ReleasedProduct releasedProduct = releasedProductMap.get(productName);
        Quantity fiveQuantity = releasedProduct.normalQuantity().add(releasedProduct.promotionQuantity());

        assertEquals(new Quantity(5),fiveQuantity );
    }

    @DisplayName("프로모션 개수가 부족하면 일반 상품을 꺼낸다")
    @Test
    void test4() {
        Stocks stocks = new Stocks();
        stocks.initialize(new CustomFileReader());
        ProductName productName = new ProductName("콜라");

        Map<ProductName, ReleasedProduct> releasedProductMap = stocks.selectProduct(productName,
                new Quantity(15));
        ReleasedProduct releasedProduct = releasedProductMap.get(productName);

        assertEquals(new Quantity(10),releasedProduct.promotionQuantity() );
        assertEquals(new Quantity(5),releasedProduct.normalQuantity() );
    }

    @DisplayName("프로모션 제품이 없으면 일반 상품만 꺼낸다")
    @Test
    void test5() {
        Stocks stocks = new Stocks();
        stocks.initialize(new CustomFileReader());
        ProductName productName = new ProductName("물");

        Map<ProductName, ReleasedProduct> releasedProductMap = stocks.selectProduct(productName,
                new Quantity(5));
        ReleasedProduct releasedProduct = releasedProductMap.get(productName);

        assertEquals(new Quantity(0),releasedProduct.promotionQuantity() );
        assertEquals(new Quantity(5),releasedProduct.normalQuantity() );
    }


}
