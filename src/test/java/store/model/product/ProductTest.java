package store.model.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("아이스크림", 1_000, 1, PromotionType.MD_RECOMMENDED);
    }

    @DisplayName("창고에 넣는 제품을 생성한다")
    @Test
    void test1() {
        assertThat(product)
                .extracting("name", "price", "count", "promotionType")
                .containsExactly("아이스크림", 1000, 1, PromotionType.MD_RECOMMENDED);
    }


    @DisplayName("이름으로 해당 제품을 반환한다.")
    @Test
    void test2() {
        assertTrue(product.isSameProduct("아이스크림"));
    }

    @DisplayName("놓친 프로모션 개수를 반환한다")
    @Test
    void test3() {
        int count = product.countMissingPromotionCount();
        assertEquals(1,count);
    }


}
