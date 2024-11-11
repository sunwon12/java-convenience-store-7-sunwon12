package store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.common.CustomFileReader;
import store.model.common.PromotionDate;
import store.model.product.PromotionType;

class PromotionDateTest {

    @DisplayName("파일을 읽어 프로모션마다 날짜를 부여하다")
    @Test
    void test1() {
        LocalDate expected = LocalDate.of(2024, 1, 1);
        PromotionDate promotionDate = new PromotionDate();
        promotionDate.initallize(new CustomFileReader());

        assertEquals(expected, promotionDate.getStartDate(PromotionType.TWO_PLUS_ONE));
    }

    @DisplayName("프로모션 타입을 받아 기간 내에 포함되지 않으면 false를 반환한다")
    @Test
    void test2() {
        LocalDateTime input = LocalDateTime.of(2000, 1, 1,1,1);
        PromotionDate promotionDate = new PromotionDate();
        promotionDate.initallize(new CustomFileReader());
        assertFalse(promotionDate.isBetweenPromotionDate(PromotionType.TWO_PLUS_ONE, input));
    }
}
