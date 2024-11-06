package store.model.product;

import java.util.Arrays;

public enum PromotionType {

    NONE(""),
    TWO_PLUS_ONE("탄산2+1"),
    FLASH_SALE("반짝할인"),
    MD_RECOMMENDED("MD추천상품");

    private final String description;

    PromotionType(String description) {
        this.description = description;
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
}
