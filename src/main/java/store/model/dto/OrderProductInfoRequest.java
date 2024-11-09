package store.model.dto;

import store.model.product.ProductName;
import store.model.product.Quantity;

public record OrderProductInfoRequest(ProductName name, Quantity amount) {

    public OrderProductInfoRequest(String name, int amount) {
         this(new ProductName(name), new Quantity(amount));
    }
}
