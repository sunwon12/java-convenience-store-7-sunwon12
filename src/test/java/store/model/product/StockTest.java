package store.model.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

    Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock();
        stock.initializeStock();
    }

    @DisplayName("재고를 초기화 한다")
    @Test
    void test1() {
    }

    @DisplayName("재고를 초기화 한다")
    @Test
    void test2() {
    }


    @DisplayName("창고에서 제품을 꺼낸다")
    @Test
    void test3() {
    }

    @DisplayName("존재하지 않는 상품을 꺼낼 시 예외를 반환한다")
    @Test
    void test4() {
    }

    @DisplayName("재고가 부족한 상품을 꺼낼 시 예외를 반환한다")
    @Test
    void test5() {
    }

    @DisplayName("프로모션 재고를 우선시해서 꺼낸다")
    @Test
    void test6() {
    }

    @DisplayName("프로모션 재고가 부족시 일반 재고를 꺼낸다")
    @Test
    void test7() {
    }
}
