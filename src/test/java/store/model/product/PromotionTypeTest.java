package store.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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


    @ParameterizedTest(name = "{0}이고 {1}개 있을 때 프로모션을 전부 챙긴 것이다")
    @CsvSource({
            "FLASH_SALE, 2",
            "MD_RECOMMENDED,4",
            "TWO_PLUS_ONE,3",
            "TWO_PLUS_ONE,6",
    })
    void test4(String promotionType, int quantity) {
        PromotionType type = PromotionType.valueOf(promotionType);
        boolean answer = type.canAllPromotionQuantity(new Quantity(quantity));

        assertTrue(answer);

    }

    @ParameterizedTest(name = "{0}이고 {1}개 있을 때 프로모션을 전부 못챙긴 것이다")
    @CsvSource({
            "FLASH_SALE, 1",
            "MD_RECOMMENDED,3",
            "TWO_PLUS_ONE,4",
            "TWO_PLUS_ONE,8",
    })
    void test5(String promotionType, int quantity) {
        PromotionType type = PromotionType.valueOf(promotionType);
        boolean answer = type.canAllPromotionQuantity(new Quantity(quantity));

        assertFalse(answer);
    }

    @ParameterizedTest(name = "프로모션 재고가 없는 상황에, {0}이고 {1}개 있을 때 {2}개는 프로모션 적용을 못 받는다.")
    @CsvSource({
            "FLASH_SALE, 3,1",
            "MD_RECOMMENDED,2,0",
            "TWO_PLUS_ONE,5,2",
    })
    void test6(String promotionType, int userQuantity, int cantPromotionQuantity) {
        PromotionType type = PromotionType.valueOf(promotionType);

        Quantity actual = type.calculateCantPromotionQuantity(new Quantity(userQuantity));

        assertEquals(new Quantity(cantPromotionQuantity), actual);
    }

    @ParameterizedTest(name = "{0}이고 {1}개 있을 때 무료로 받을 수 있는 수량은 {2}개이다")
    @CsvSource({
            "FLASH_SALE, 6, 3",      // 2개당 1개 무료
            "FLASH_SALE, 4, 2",
            "MD_RECOMMENDED, 6, 3",   // 2개당 1개 무료
            "MD_RECOMMENDED, 4, 2",
            "TWO_PLUS_ONE, 6, 2",    // 3개당 1개 무료
            "TWO_PLUS_ONE, 9, 3",
            "NONE, 6, 0"             // 프로모션 없음
    })
    void test7(String promotionType, int quantity, int expectedFree) {
        PromotionType type = PromotionType.valueOf(promotionType);
        Quantity purchaseQuantity = new Quantity(quantity);
        Quantity expectedQuantity = new Quantity(expectedFree);

        Quantity actualFreeQuantity = type.getFreePromotionQuantity(purchaseQuantity);

        assertEquals(expectedQuantity, actualFreeQuantity);
    }
}
