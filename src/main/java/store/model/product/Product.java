package store.model.product;

public class Product {

    private final ProductName productName;
    private final Money price;
    private final Promotion promotion;

    public Product(ProductName productName, Money price, Promotion promotion) {
        this.productName = productName;
        this.price = price;
        this.promotion = promotion;
    }
}

