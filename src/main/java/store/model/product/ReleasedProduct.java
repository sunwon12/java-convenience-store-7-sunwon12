package store.model.product;

public record ReleasedProduct(Product product, Quantity promotionQuantity,
                              Quantity normalQuantity, PromotionType promotionType) {

    public Quantity getMissingPromotion() {
        return promotionType.calculateFreeQuantity(promotionQuantity);
    }
}
