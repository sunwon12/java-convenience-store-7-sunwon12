package store.model.product;

public class Product {

    private final ProductName name;
    private final Money price;
    private final PromotionType promotionType;

    public Product(ProductName name, Money price, PromotionType promotionType) {
        this.name = name;
        this.price = price;
        this.promotionType = promotionType;
    }
}

