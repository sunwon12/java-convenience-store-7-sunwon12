package store.model.product;

import java.util.Collection;
import java.util.Map;

public record ReleasedProducts(Map<ProductName, ReleasedProduct> products) {

    public Money getTotalMoney() {
        return products.values().stream()
                .map(ReleasedProduct::getTotalMoney)
                .reduce(Money.ZERO, Money::add);
    }

    public Collection<ReleasedProduct> getReleasedProducts() {
        return products.values();
    }
}
