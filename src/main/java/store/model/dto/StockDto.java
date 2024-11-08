package store.model.dto;

import store.model.product.Money;
import store.model.product.Product;
import store.model.product.ProductName;
import store.model.product.PromotionType;

public record StockDto(String name, int price, int quantity, PromotionType promotionType) {

    public Product toProduct() {
        return new Product(
                new ProductName(name),
                new Money(quantity),
                promotionType
        );
    }
}
