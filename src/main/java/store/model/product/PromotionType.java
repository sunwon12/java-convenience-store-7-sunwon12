package store.model.product;

import java.util.Arrays;
import java.util.function.ToIntFunction;

public enum PromotionType {

    NONE("", count -> 0),
    TWO_PLUS_ONE("탄산2+1", count -> count % 3 == 2 ? 1 : 0),
    FLASH_SALE("반짝할인", count -> count % 2 == 1 ? 1 : 0),
    MD_RECOMMENDED("MD추천상품", count -> count % 2 == 1 ? 1 : 0);

    private final String description;
    private final ToIntFunction<Integer> calculator;

    PromotionType(String description, ToIntFunction<Integer> calculator) {
        this.description = description;
        this.calculator = calculator;
    }

    public static PromotionType from(String description) {
        return Arrays.stream(PromotionType.values())
                .filter(promotionType -> promotionType.description.equals(description))
                .findFirst()
                .orElse(PromotionType.NONE);
    }

    public String getDescription() {
        return description;
    }

    public int calculateMissingPromotionCount(int itemCount) {
        return calculator.applyAsInt(itemCount);
    }
}
