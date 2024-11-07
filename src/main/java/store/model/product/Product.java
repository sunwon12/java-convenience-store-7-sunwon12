package store.model.product;

public class Product {

    private final ProductName name;
    private final Money price;
    private final Promotion promotion;

    public Product(ProductName name, Money price, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
    }
}

