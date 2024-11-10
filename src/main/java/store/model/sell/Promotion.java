package store.model.sell;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import store.model.ShoppingCart;
import store.model.product.ProductName;
import store.model.product.Quantity;
import store.model.product.ReleasedProduct;
import store.model.product.ReleasedProducts;

public class Promotion {

    public Map<ProductName, Quantity> getFreePromotion(ShoppingCart shoppingCart) {
        ReleasedProducts releasedProducts = new ReleasedProducts(shoppingCart.getProducts());
        return releasedProducts.products().entrySet().stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        this::applyPromotion
                ));
    }

    private Quantity applyPromotion(Entry<ProductName, ReleasedProduct> entry) {
        ReleasedProduct product = entry.getValue();
        return product.getFreePromotionQuantity();
    }
}