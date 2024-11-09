package store.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import store.model.product.ProductName;
import store.model.product.Quantity;
import store.model.product.ReleasedProduct;

public class ShoppingCart {

    private Map<ProductName, ReleasedProduct> products;

    public ShoppingCart() {
        this.products = new LinkedHashMap<>();
    }

    public ShoppingCart(Map<ProductName, ReleasedProduct> products) {
        this.products = products;
    }

    public ShoppingCart add(Map<ProductName, ReleasedProduct> newProducts) {
        newProducts.forEach(this::addProduct);
        return this;
    }

    private void addProduct(ProductName name, ReleasedProduct newProduct) {
        products.merge(name, newProduct,
                (existingProduct, addedProduct) -> new ReleasedProduct(
                        existingProduct.product(),
                        existingProduct.promotionQuantity().add(addedProduct.promotionQuantity()),
                        existingProduct.normalQuantity().add(addedProduct.normalQuantity()),
                        existingProduct.promotionType()
                ));
    }

    public Map<ProductName, Quantity> checkMissingPromotion() {
        Map<ProductName, Quantity> returnMap = new HashMap<>();
        for (Map.Entry<ProductName, ReleasedProduct> entry : products.entrySet()) {
            Quantity quantity = entry.getValue().getMissingPromotion();
            if(!quantity.isZero()) {
                returnMap.put(entry.getKey(), quantity);
            }
        }
        return returnMap;
    }

    public Map<ProductName, ReleasedProduct> getProducts() {
        return products;
    }
}
