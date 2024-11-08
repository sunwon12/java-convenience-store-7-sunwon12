package store.model.product;

public class Product {

    private final ProductName name;
    private final Money price;

    public Product(ProductName name, Money price) {
        this.name = name;
        this.price = price;
    }
}

