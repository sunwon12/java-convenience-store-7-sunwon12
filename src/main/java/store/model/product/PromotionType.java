package store.model.product;

import java.util.Arrays;
import java.util.function.Function;

public enum PromotionType {

    NONE("",
            quantity -> Quantity.ZERO,
            quantity -> false,
            quantity -> Quantity.ZERO),
    TWO_PLUS_ONE("탄산2+1",
            quantity -> quantity.remainder(3).getValue() == 2 ? new Quantity(1) : Quantity.ZERO,
            quantity -> quantity.getValue() % 3 == 0,
            quantity -> quantity.remainder(3)),
    FLASH_SALE("반짝할인",
            quantity -> quantity.remainder(2).getValue() == 1 ? new Quantity(1) : Quantity.ZERO,
            quantity -> quantity.getValue() % 2 == 0,
            quantity -> quantity.remainder(2)),
    MD_RECOMMENDED("MD추천상품",
            quantity -> quantity.remainder(2).getValue() == 1 ? new Quantity(1) : Quantity.ZERO,
            quantity -> quantity.getValue() % 2 == 0,
            quantity -> quantity.remainder(2));

    private final String description;
    private final Function<Quantity, Quantity> freeQuantityCalculator;
    private final Function<Quantity, Boolean> promotionQuantityChecker;
    private final Function<Quantity, Quantity> nonPromotionQuantityCalculator;

    PromotionType(String description,
                  Function<Quantity, Quantity> freeQuantityCalculator,
                  Function<Quantity, Boolean> promotionQuantityChecker,
                  Function<Quantity, Quantity> nonPromotionQuantityCalculator) {
        this.description = description;
        this.freeQuantityCalculator = freeQuantityCalculator;
        this.promotionQuantityChecker = promotionQuantityChecker;
        this.nonPromotionQuantityCalculator = nonPromotionQuantityCalculator;
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

    public boolean canAllPromotionQuantity(Quantity quantity) {
        if (this == NONE || quantity.isZero()) {
            return false;
        }
        return promotionQuantityChecker.apply(quantity);
    }

    public Quantity calculateCantPromotionQuantity(Quantity promotionQuantity) {
        if (this == NONE || promotionQuantity.isZero()) {
            return Quantity.ZERO;
        }
        return nonPromotionQuantityCalculator.apply(promotionQuantity);
    }
}
