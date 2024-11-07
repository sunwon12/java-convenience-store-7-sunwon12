package store.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.product.Stock;

class ShopingCartTest {

    Stock stock;
    ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        stock = new Stock();
        stock.initializeStock();
        shoppingCart = new ShoppingCart(stock);
    }

    @DisplayName("쇼핑카트에 상품을 담는다")
    @Test
    void test1() {
    }

    @DisplayName("놓친 프로모션을 챙긴다")
    @Test
    void test2() {
    }

    @DisplayName("놓친 프로모션이 여러 종류일 때도 챙긴다")
    @Test
    void test3() {
    }

}
