package store.model.product;

import java.util.ArrayList;
import java.util.List;
import store.model.CustomFileReader;
import store.model.ErrorMessage;
import store.model.dto.StockProductDto;

public class Stock {

    private List<Product> products;

    public Stock() {
        this.products = new ArrayList<>();
    }

    /**
     * 파일에서 물건 목록을 가져와 창고에 넣어준다
     */
    public void initializeStock() {
        List<StockProductDto> stockProductDtos = new CustomFileReader().loadProducts();

        List<Product> products = new ArrayList<>();
        for (StockProductDto dto : stockProductDtos) {
            Product product = new Product(
                    dto.name(), dto.price(), dto.count(), dto.promotionType());
            products.add(product);
        }
        List<Product> productsWithZeroNomal = addZeroNormalProducts(products);
        this.products = productsWithZeroNomal;
    }

    private List<Product> addZeroNormalProducts(List<Product> originalProducts) {
        List<Product> result = new ArrayList<>(originalProducts);

        for (int i = 0; i < originalProducts.size(); i++) {
            Product current = originalProducts.get(i);
            if (shouldAddNormal(current, originalProducts)) {
                result.add(i + 1, createNormalProduct(current.getName(), current.getPrice()));
            }
        }

        return result;
    }

    private boolean shouldAddNormal(Product product, List<Product> allProducts) {
        return product.isPromotion()
                && !hasNormal(product.getName(), allProducts);
    }

    private boolean hasNormal(String name, List<Product> products) {
        return products.stream()
                .anyMatch(sp -> sp.getName().equals(name)
                        && sp.getPromotionType() == PromotionType.NONE);
    }

    private Product createNormalProduct(String name, int price) {
        return new Product(
                name,
                price,
                0,
                PromotionType.NONE
        );
    }


    /**
     * 손님에게 물건을 꺼내주다
     */
    public List<Product> giveProduct(String name, int amount) {
        if (!hasProduct(name)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage());
        }
        List<Product> preferredPromotionProduct = getPreferredPromotionProduct(name, amount);
        applyChangesToStock(preferredPromotionProduct);
        return preferredPromotionProduct;
    }

    private boolean hasProduct(String name) {
        return this.products.stream()
                .anyMatch(product -> product.isSameProduct(name));
    }

    private List<Product> getPreferredPromotionProduct(String name, int amount) {
        List<Product> result = new ArrayList<>();
        int remainingCount = processPromotionProducts(result, name, amount);
        processNormalProducts(result, name, remainingCount);
        return result;
    }

    private int processPromotionProducts(List<Product> result, String name, int amount) {
        if (!hasPromotionMoreThanOne(name)) {
            return amount;
        }
        Product copyPromotion = createPromotionCopy(name, amount);
        result.add(copyPromotion);
        return amount - copyPromotion.getCount();
    }

    private void processNormalProducts(List<Product> result, String name, int remainingCount) {
        if (hasNormalMoreThanOne(name) && remainingCount > 0) {
            Product copyNormal = createNormalCopy(name, remainingCount);
            result.add(copyNormal);
        }
    }

    private Product createPromotionCopy(String name, int amount) {
        Product promotion = findPromotion(name);
        return copyPromotionProductsWithMinus(promotion, amount);
    }

    private Product createNormalCopy(String name, int amount) {
        Product normal = findNormal(name);
        return copyNormalProductsWithMinus(normal, amount);
    }

    private boolean hasPromotionMoreThanOne(String name) {
        return this.products.stream()
                .filter(product -> product.isSameProduct(name))
                .filter(Product::isPromotion)
                .anyMatch(Product::hasProduct);
    }

    private Product findPromotion(String name) {
        return this.products.stream()
                .filter(product -> product.isSameProduct(name))
                .filter(Product::isPromotion)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage()));
    }


    private boolean hasNormalMoreThanOne(String name) {
        return this.products.stream()
                .filter(product -> product.isSameProduct(name))
                .filter(product -> !product.isPromotion())
                .anyMatch(Product::hasProduct);
    }

    private Product findNormal(String name) {
        return this.products.stream()
                .filter(product -> product.isSameProduct(name))
                .filter(product -> !product.isPromotion())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage()));
    }


    private Product copyNormalProductsWithMinus(Product normal, int remainingAmount) {
        if (normal.getCount() < remainingAmount) {
            throw new IllegalArgumentException(ErrorMessage.NOT_ENOUGH_PRODUCT.getMessage());
        }
        Product clone = normal.deepCopy();
        clone.updateCount(remainingAmount);
        return clone;
    }

    private Product copyPromotionProductsWithMinus(Product promotionProduct, int amount) {
        int count = promotionProduct.getCount();
        Product clone = promotionProduct.deepCopy();
        if (count > amount) {
            clone.updateCount(amount);
        }
        return clone;
    }

    private void applyChangesToStock(List<Product> preferredPromotionProduct) {
        for (Product givedProduct : preferredPromotionProduct) {
            applyProductChange(givedProduct);
        }
    }

    private void applyProductChange(Product givedProduct) {
        for (Product product : products) {
            if (product.isSameNameAndPromotionType(givedProduct)) {
                product.minusCount(givedProduct.getCount());
            }
        }
    }

    public List<Product> getStockProducts() {
        return this.products;
    }
}
