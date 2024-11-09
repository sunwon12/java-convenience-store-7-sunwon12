package store.model.product;

import java.util.Arrays;
import java.util.function.Function;
public enum PromotionType {
    NONE("", quantity -> Quantity.ZERO),
    TWO_PLUS_ONE("탄산2+1", quantity ->
            quantity.remainder(3).getValue() == 2 ? new Quantity(1) : Quantity.ZERO),
    FLASH_SALE("반짝할인", quantity ->
            quantity.remainder(2).getValue() == 1 ? new Quantity(1) : Quantity.ZERO),
    MD_RECOMMENDED("MD추천상품", quantity ->
            quantity.remainder(2).getValue() == 1 ? new Quantity(1) : Quantity.ZERO);

    private final String description;
    private final Function<Quantity, Quantity> freeQuantityCalculator;

    PromotionType(String description, Function<Quantity, Quantity> freeQuantityCalculator) {
        this.description = description;
        this.freeQuantityCalculator = freeQuantityCalculator;
    }

    public Quantity calculateFreeQuantity(Quantity quantity) {
        if (this == NONE || quantity.isZero()) {
            return Quantity.ZERO;
        }
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

    @Override
    public String toString() {
        return description;
    }
}
