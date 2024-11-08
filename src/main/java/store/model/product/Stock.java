package store.model.product;

public class Stock {
    private final Product product;
    private Quantity promotionalQuantity;
    private Quantity normalQuantity;
    private final PromotionType promotionType;

    public Stock(Product product,Quantity promotionalQuantity, Quantity normalQuantity, PromotionType promotionType) {
        this.product =product;
        this.normalQuantity = normalQuantity;
        this.promotionalQuantity = promotionalQuantity;
        this.promotionType = promotionType;
    }

    public int getPrice() {
        return normalQuantity.getValue();
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public Quantity getPromotionalQuantity() {
        return promotionalQuantity;
    }

    public Quantity getNormalQuantity() {
        return normalQuantity;
    }

    public boolean hasPromotion() {
        return !promotionalQuantity.isZero();
    }
}
