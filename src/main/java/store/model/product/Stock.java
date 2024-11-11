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
        takeOut(stockStatus);
        return new ReleasedProduct(
                product,
                stockStatus.getPromotionalAvailable(),
                stockStatus.getNormalRequired(),
                promotionType
        );
    }

    private void takeOut(StockStatus stockStatus) {
        this.promotionalQuantity = this.promotionalQuantity.subtract(stockStatus.getPromotionalAvailable());
        this.normalQuantity = this.normalQuantity.subtract(stockStatus.getNormalRequired());
    }

    private void validateQuantity(Quantity requestedQuantity) {
        StockStatus stockStatus = new StockStatus(promotionalQuantity, normalQuantity, requestedQuantity);
        stockStatus.validateStock();
    }

    public int getPrice() {
        return product.getPrice().getValue();
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

    public boolean hadPromotion() {
        return getPromotionType() != PromotionType.NONE &&
                getPromotionalQuantity().isZero();
    }

    public boolean hasEnoughPromotionStock(Quantity requestedQuantity) {
        StockStatus stockStatus = new StockStatus(promotionalQuantity, normalQuantity, requestedQuantity);
        return stockStatus.hasEnoughPromotionStock();
    }


    public Product getProduct() {
        return product;
    }

    public Stock add(ReleasedProduct releasedProduct) {
        Quantity promotion = promotionalQuantity.add(releasedProduct.promotionQuantity());
        Quantity normal = normalQuantity.subtract(releasedProduct.normalQuantity());
        return new Stock(this.product, promotion, normal, this.promotionType)    ;
    }
}
