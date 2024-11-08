package store.model.product;

public record ReleasedProduct(Product product, Quantity promotionQuantity, Quantity normalQuantity) {
}
