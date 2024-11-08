package store;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.CustomFileReader;
import store.model.product.ProductName;
import store.model.product.Stocks;

class StocksTest {

    @DisplayName("재고를 초기화하다")
    @Test
    void test1() {
        Stocks stocks = new Stocks();
        stocks.initialize(new CustomFileReader());

        assertTrue(stocks.hasStock(new ProductName("콜라")));
    }
}
