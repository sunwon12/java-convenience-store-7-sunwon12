package store.model.product;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ShoppingCart {

    private Map<ProductName, ReleasedProduct> products;

    public ShoppingCart() {
        this.products = new LinkedHashMap<>();
    }

    public ShoppingCart(Map<ProductName, ReleasedProduct> products) {
        this.products = products;
    }

    public void add(Map<ProductName, ReleasedProduct> newProducts) {
        newProducts.forEach(this::addProduct);
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

    public Map<ProductName, ReleasedProduct> checkMissingPromotion() {
        Map<ProductName, ReleasedProduct> returnMap = new HashMap<>();
        for (Map.Entry<ProductName, ReleasedProduct> entry : products.entrySet()) {
            ReleasedProduct missingPromotion = entry.getValue().getMissingPromotion();
            if (!missingPromotion.isPromotionQuantityZero()) {
                returnMap.put(entry.getKey(), missingPromotion);
            }
        }
        return returnMap;
    }

    public Map<ProductName, ReleasedProduct> getProducts() {
        return products;
    }

    /**
     * 프로모션 상품을 가지고 있는데, 프로모션 상품이 적용이 안되는 상품 개수를 반환한다
     */
    public Map<ProductName, ReleasedProduct> calculateNonPromotionQuantity() {
        return products.entrySet().stream()
                .filter(this::hasNonPromotionQuantity)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        this::calculateNonPromotionQuantity
                ));
    }

    private boolean hasNonPromotionQuantity(Map.Entry<ProductName, ReleasedProduct> entry) {
        ReleasedProduct product = entry.getValue();
        Quantity promotionQuantity = product.promotionQuantity();
        return !promotionQuantity.isZero()
                && !product.promotionType().canAllPromotionQuantity(promotionQuantity);
    }

    private ReleasedProduct calculateNonPromotionQuantity(Map.Entry<ProductName, ReleasedProduct> entry) {
        ReleasedProduct releasedProduct = entry.getValue();
        return new ReleasedProduct(releasedProduct.product(),
                releasedProduct.getCantPromotionQuantity(),
                releasedProduct.normalQuantity(),
                releasedProduct.promotionType());
    }


    public void subtract(Entry<ProductName, ReleasedProduct> subtractEntry) {
        ProductName productName = subtractEntry.getKey();
        ReleasedProduct subtractProduct = subtractEntry.getValue();

        ReleasedProduct releasedProduct = products.get(productName);
        ReleasedProduct newProduct = releasedProduct.subtract(subtractProduct);
        products.put(productName, newProduct);
    }

    public Money getTotalMoney() {
        return products.values().stream()
                .map(ReleasedProduct::getTotalMoney)
                .reduce(Money.ZERO, Money::add);
    }

    public Money getNonPromotionTotalMoney() {
        return products.entrySet().stream()
                .map(this::calculateNonPromotionQuantity)
                .map(ReleasedProduct::getTotalMoney)
                .reduce(Money.ZERO, Money::add);
    }
}
