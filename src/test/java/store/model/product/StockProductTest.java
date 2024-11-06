package store.model.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockProductTest {

    @DisplayName("창고에 넣는 제품을 생성한다")
    @Test
    void test1() {

        StockProduct stockProduct = new StockProduct("iceCream", 1_000, 1,PromotionType.MD_RECOMMENDED );

        assertThat(stockProduct)
                .extracting("name", "price","count", "promotionType")
                .containsExactly("iceCream", 1000, 1, PromotionType.MD_RECOMMENDED);
    }
}
