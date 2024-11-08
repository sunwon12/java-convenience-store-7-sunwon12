package store.model.product;

public class Stock {
    private final Product product;
    private Quantity promotionalQuantity;
    private Quantity normalQuantity;

    public Stock(Product product, Quantity normalQuantity) {
        this.product =product;
        this.normalQuantity = normalQuantity;
        this.promotionalQuantity = Quantity.ZERO;
    }

    public Stock(Product product,Quantity promotionalQuantity, Quantity normalQuantity) {
        this.product =product;
        this.normalQuantity = normalQuantity;
        this.promotionalQuantity = promotionalQuantity;
    }
}
