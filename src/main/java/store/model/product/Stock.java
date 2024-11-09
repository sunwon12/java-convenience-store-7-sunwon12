package store.model.product;

public class Stock {
    private final Product product;
    private Quantity promotionalQuantity;
    private Quantity normalQuantity;
    private final PromotionType promotionType;

    public Stock(Product product, Quantity promotionalQuantity, Quantity normalQuantity, PromotionType promotionType) {
        this.product = product;
        this.normalQuantity = normalQuantity;
        this.promotionalQuantity = promotionalQuantity;
        this.promotionType = promotionType;
    }

    public ReleasedProduct release(Quantity requestedQuantity) {
        Quantity promotion = calculatePromotionalQuantity(requestedQuantity);
        Quantity normal = requestedQuantity.subtract(promotion);

        return new ReleasedProduct(
                product,
                promotion,
                normal,
                promotionType
        );
    }

    private Quantity calculatePromotionalQuantity(Quantity requestedQuantity) {
        if (promotionalQuantity.isGreaterThanOrEqual(requestedQuantity)) {
            return requestedQuantity;
        }
        return promotionalQuantity;
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

    public boolean hasEnoughStock(Quantity quantity) {
        if (promotionalQuantity.isGreaterThan(quantity)){
            return true;
        }
        Quantity subtract = quantity.subtract(promotionalQuantity);
        if(normalQuantity.isLessThan(subtract)){
            return false;
        }
        return true;
    }

    public Product getProduct() {
        return product;
    }
}
