package store.model;

import store.model.product.PromotionType;

public record StockProductDto(
        String name,
        int price,
        int count,
        PromotionType promotionType

) {
}
