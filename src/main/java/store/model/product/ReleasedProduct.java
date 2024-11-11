package store.model.product;

public record ReleasedProduct(Product product, Quantity promotionQuantity,
                              Quantity normalQuantity, PromotionType promotionType) {

    public ReleasedProduct getMissingPromotion() {
        return new ReleasedProduct(this.product,
                promotionType.calculateFreeQuantity(promotionQuantity),
                this.normalQuantity,
                this.promotionType);
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

    public Money getTotalMoney() {
        return product.getPrice()
                .multiply(getTotalQuantity().getValue());
    }

    public Quantity getFreePromotionQuantity() {
        return promotionType.getFreePromotionQuantity(promotionQuantity);
    }

    public Money getPrice() {
        return product.getPrice();
    }

    public boolean isPromotionQuantityZero() {
        return promotionQuantity.isZero();
    }
}
