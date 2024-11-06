package store.model.product;

public class Product {

    private final ProductId productId;
    private final PromotionType promotionType;

    public Product(ProductId productId, PromotionType promotionType) {
        this.productId = productId;
        this.promotionType = promotionType;
    }
//
//    protected ProductId getProductId() {
//        return productId;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product other = (Product) o;
        return productId.equals(other.productId) && promotionType.equals(other.promotionType);
    }
}
