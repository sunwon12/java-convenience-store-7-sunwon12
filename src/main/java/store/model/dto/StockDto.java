package store.model.dto;

import store.model.product.PromotionType;

public record StockDto(String name, int price, int quantity, PromotionType promotionType) {
}
