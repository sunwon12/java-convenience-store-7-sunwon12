package store.model.sell;

import java.util.Map;
import store.model.product.ProductName;
import store.model.product.ReleasedProduct;

public class Receipt {

    private Map<ProductName, ReleasedProduct> products;

    public Receipt(Map<ProductName, ReleasedProduct> products) {
        this.products = products;
    }
}
