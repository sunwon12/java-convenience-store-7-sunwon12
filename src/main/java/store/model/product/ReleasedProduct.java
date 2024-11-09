package store.model.product;

public record ReleasedProduct(Product product, Quantity promotionQuantity,
                              Quantity normalQuantity, PromotionType promotionType) {

    public Quantity getMissingPromotion() {
        return promotionType.calculateFreeQuantity(promotionQuantity);
    }

    public Quantity getCantPromotionQuantity() {
        return promotionType.calculateCantPromotionQuantity(promotionQuantity);
    }

    public Quantity getTotalQuantity() {
        return normalQuantity.add(promotionQuantity);
    }

    public ReleasedProduct subtract(ReleasedProduct removingProduct) {
        Quantity promotion = promotionQuantity.subtract(removingProduct.promotionQuantity);
        Quantity normal = normalQuantity.subtract(removingProduct.normalQuantity);
        return new ReleasedProduct(product, promotion, normal, promotionType);
    }
}
