package store.view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SimpleValidatorTest {

    SimpleValidator simpleValidator = new SimpleValidator();

    @DisplayName("유효한 상품 이름과 수량 입력 포맷을 검증한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "[사이다-2]",
            "[사이다-2],[감자칩-1]",
            "[사이다-2],[감자칩-1],[콜라-3]",
            "[사이다-2],    [감자칩-1],     [콜라-3]",
            "[음료-3],[과자-4],[사탕-1],[초콜릿-5],[젤리-2]"
    })
    void test1(String input) {
        assertDoesNotThrow(() -> simpleValidator.validateProductNameAndCount(input));
    }

    @DisplayName("잘못된 상품 이름과 수량 입력 포맷은 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "[2-사이다]",           // 숫자가 앞에 옴
            "사이다-2",             // 대괄호 없음
            "[사이다-a]",           // 숫자가 아닌 수량
            "[사이다-2],",          // 쉼표로 끝남
            "[사이다-2],[감자칩]",   // 수량 없음
            "[사이다]",             // 수량 없음
            "[]"                   // 비어있음
    })
    void test2(String input) {
        assertThrows(IllegalArgumentException.class, () ->
                simpleValidator.validateProductNameAndCount(input)
        );
    }

    @DisplayName("Yes or No 입력 포맷을 검증한다")
    @ParameterizedTest
    @ValueSource(strings = {"Y", "N"})
    void test3(String input) {
        assertDoesNotThrow(() -> simpleValidator.validateYesOrNo(input));
    }

    @DisplayName("잘못된 Yes or No 입력 포맷은 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"YES", "NO", "Umm.."})
    void test4(String input) {
        assertThrows(IllegalArgumentException.class, () ->
                simpleValidator.validateYesOrNo(input)
        );
    }
}
