package store.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
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
        assertEquals("콜라", stock.getStockProducts().getFirst().getName());
    }

    @DisplayName("재고를 초기화 한다")
    @Test
    void test2() {
        assertEquals("오렌지주스", stock.getStockProducts().get(4).getName());
    }


    @DisplayName("창고에서 제품을 꺼낸다")
    @Test
    void test3() {
        List<Product> product = stock.giveProduct("콜라", 1);
        assertEquals("콜라", product.getFirst().getName());
    }

    @DisplayName("존재하지 않는 상품을 꺼낼 시 예외를 반환한다")
    @Test
    void test4() {
        assertThrows(IllegalArgumentException.class, () ->
                stock.giveProduct("존재하지 않는 상품", 1)
        );
    }

    @DisplayName("재고가 부족한 상품을 꺼낼 시 예외를 반환한다")
    @Test
    void test5() {
        assertThrows(IllegalArgumentException.class, () ->
                stock.giveProduct("콜라", 1000)
        );
    }

    @DisplayName("프로모션 재고를 우선시해서 꺼낸다")
    @Test
    void test6() {
        List<Product> products = stock.giveProduct("콜라", 10);
        assertEquals(PromotionType.TWO_PLUS_ONE, products.getFirst().getPromotionType());
    }

    @DisplayName("프로모션 재고가 부족시 일반 재고를 꺼낸다")
    @Test
    void test7() {
        List<Product> products = stock.giveProduct("콜라", 15);
        assertEquals(PromotionType.TWO_PLUS_ONE, products.get(0).getPromotionType());
        assertEquals(PromotionType.NONE, products.get(1).getPromotionType());
    }
}
