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
        validateQuantity(requestedQuantity);
        StockStatus stockStatus = new StockStatus(promotionalQuantity, normalQuantity, requestedQuantity);

        return new ReleasedProduct(
                product,
                stockStatus.getPromotionalAvailable(),
                stockStatus.getNormalRequired(),
                promotionType
        );
    }

    private void validateQuantity(Quantity requestedQuantity) {
        StockStatus stockStatus = new StockStatus(promotionalQuantity, normalQuantity, requestedQuantity);
        stockStatus.validateStock();
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

    public boolean hasEnoughStock(Quantity requestedQuantity) {
        StockStatus stockStatus = new StockStatus(promotionalQuantity, normalQuantity, requestedQuantity);
        return stockStatus.hasEnoughStock();
    }

    public Product getProduct() {
        return product;
    }
}
