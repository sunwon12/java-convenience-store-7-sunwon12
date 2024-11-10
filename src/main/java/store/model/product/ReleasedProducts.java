package store.model.product;

import java.util.Map;

public record ReleasedProducts(Map<ProductName, ReleasedProduct> products) {

    public Money getTotalMoney() {
        return products.values().stream()
                .map(ReleasedProduct::getTotalMoney)
                .reduce(Money.ZERO, Money::add);
    }
}
