package store.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PromotionTypeTest {

    @ParameterizedTest(name = "반짝할인 상품 {0}개 구매 시 무료 증정 수량은 {1}개이다")
    @CsvSource({
            "1, 1",
            "2, 0",
            "3, 1",
            "4, 0",
            "5, 1"
    })
    void test1(int input, int expected) {
        Quantity expectedQuantity = new Quantity(expected);
        Quantity inputQuantity = new Quantity(input);
        Quantity missing = PromotionType.FLASH_SALE.calculateFreeQuantity(inputQuantity);
        assertEquals(expectedQuantity, missing);
    }

    @ParameterizedTest(name = "MD 추천 상품 {0}개 구매 시 무료 증정 수량은 {1}개이다")
    @CsvSource({
            "1, 1",
            "2, 0",
            "3, 1",
            "4, 0",
            "5, 1"
    })
    void test2(int input, int expected) {
        Quantity expectedQuantity = new Quantity(expected);
        Quantity inputQuantity = new Quantity(input);
        Quantity missing = PromotionType.MD_RECOMMENDED.calculateFreeQuantity(inputQuantity);
        assertEquals(expectedQuantity, missing);
    }

    @ParameterizedTest(name = "탄산2+1 상품 {0}개 구매 시 무료 증정 수량은 {1}개이다")
    @CsvSource({
            "2, 1",   // input, expected
            "3, 0",
            "5, 1"
    })
    void test3(int input, int expected) {
        Quantity expectedQuantity = new Quantity(expected);
        Quantity inputQuantity = new Quantity(input);

        Quantity missing = PromotionType.TWO_PLUS_ONE.calculateFreeQuantity(inputQuantity);

        assertEquals(expectedQuantity, missing);
    }
}
