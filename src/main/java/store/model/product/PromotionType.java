package store.model.product;

import java.util.Arrays;
import java.util.function.Function;
public enum PromotionType {
    NONE("", quantity -> 0),
    TWO_PLUS_ONE("탄산2+1", quantity -> quantity / 3),
    MD_RECOMMENDED("MD추천상품", quantity -> quantity / 2),
    FLASH_SALE("반짝할인", quantity -> quantity / 2);

    private final String description;
    private final Function<Integer, Integer> freeQuantityCalculator;

    PromotionType(String description, Function<Integer, Integer> freeQuantityCalculator) {
        this.description = description;
        this.freeQuantityCalculator = freeQuantityCalculator;
    }

    public int calculateFreeQuantity(int quantity) {
        return freeQuantityCalculator.apply(quantity);
    }

    public boolean isApplicable(int quantity) {
        return this != NONE && quantity >= 2;
    }

    public static PromotionType from(String description) {
        return Arrays.stream(values())
                .filter(type -> type.description.equals(description))
                .findFirst()
                .orElse(NONE);
    }

    public String getDescription() {
        return description;
    }

    public boolean isPromotion() {
        return this != NONE;
    }
}
