package store.model.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StockStatusTest {

    @DisplayName("프로모션 재고와 일반 재고의 합이 요청 수량보다 크거나 같으면 true를 반환한다")
    @ParameterizedTest
    @CsvSource({
            "5, 5, 10",  // 정확히 같은 경우
            "6, 5, 10",  // 더 많은 경우
            "3, 8, 10"   // 일반 재고가 더 많은 경우
    })
    void test1(int promotional, int normal, int requested) {
        StockStatus stockStatus = new StockStatus(
                new Quantity(promotional),
                new Quantity(normal),
                new Quantity(requested)
        );

        assertThat(stockStatus.hasEnoughStock()).isTrue();
    }

    @Test
    @DisplayName("총 재고가 요청 수량보다 적으면 false를 반환한다")
    void test2() {
        StockStatus stockStatus = new StockStatus(
                new Quantity(3),
                new Quantity(5),
                new Quantity(10)
        );

        assertThat(stockStatus.hasEnoughStock()).isFalse();
    }

    @DisplayName("프로모션 적용 가능한 수량을 계산한다")
    @ParameterizedTest
    @CsvSource({
            "5, 5, 3, 3",   // 요청 수량이 프로모션 재고보다 적은 경우
            "3, 5, 5, 3",   // 요청 수량이 프로모션 재고보다 많은 경우
            "5, 5, 5, 5"    // 요청 수량과 프로모션 재고가 같은 경우
    })
    void test2(int promotional, int normal, int requested, int expected) {
        StockStatus stockStatus = new StockStatus(
                new Quantity(promotional),
                new Quantity(normal),
                new Quantity(requested)
        );

        Quantity result = stockStatus.getPromotionalAvailable();

        assertThat(result).isEqualTo(new Quantity(expected));
    }

    @DisplayName("일반 상품으로 구매해야 하는 수량을 계산한다")
    @ParameterizedTest
    @CsvSource({
            "5, 5, 3, 0",   // 프로모션 재고가 충분한 경우
            "3, 5, 5, 2",   // 프로모션 재고가 부족한 경우
            "0, 5, 5, 5"    // 프로모션 재고가 없는 경우
    })
    void test3(int promotional, int normal, int requested, int expected) {
        StockStatus stockStatus = new StockStatus(
                new Quantity(promotional),
                new Quantity(normal),
                new Quantity(requested)
        );

        Quantity result = stockStatus.getNormalRequired();

        assertThat(result).isEqualTo(new Quantity(expected));
    }

    @Test
    @DisplayName("재고가 부족하면 예외를 던진다")
    void test4() {
        StockStatus stockStatus = new StockStatus(
                new Quantity(3),
                new Quantity(5),
                new Quantity(10)
        );

        assertThatThrownBy(stockStatus::validateStock)
                .isInstanceOf(IllegalArgumentException.class);
    }
}
