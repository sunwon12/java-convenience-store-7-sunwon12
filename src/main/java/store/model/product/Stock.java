package store.model.product;

import java.util.ArrayList;
import java.util.List;
import store.model.CustomFileReader;
import store.model.ErrorMessage;
import store.model.dto.StockProductDto;

public class Stock {

    private List<StockProduct> stockProducts;

    public Stock() {
        this.stockProducts = new ArrayList<>();
    }

    /**
     * 파일에서 물건 목록을 가져와 창고에 넣어준다
     */
    public void initializeStock() {
        List<StockProductDto> stockProductDtos = new CustomFileReader().loadProducts();

        List<StockProduct> products = new ArrayList<>();
        for (StockProductDto dto : stockProductDtos) {
            StockProduct stockProduct = new StockProduct(
                    dto.name(), dto.price(), dto.count(), dto.promotionType());
            products.add(stockProduct);
        }
        List<StockProduct> productsWithZeroNomal = addZeroNormalProducts(products);
        this.stockProducts = productsWithZeroNomal;
    }

    private List<StockProduct> addZeroNormalProducts(List<StockProduct> originalProducts) {
        List<StockProduct> result = new ArrayList<>(originalProducts);

        for (int i = 0; i < originalProducts.size(); i++) {
            StockProduct current = originalProducts.get(i);
            if (shouldAddNormal(current, originalProducts)) {
                result.add(i + 1, createNormalProduct(current.getName(), current.getPrice()));
            }
        }

        return result;
    }

    private boolean shouldAddNormal(StockProduct product, List<StockProduct> allProducts) {
        return product.isPromotion()
                && !hasNormal(product.getName(), allProducts);
    }

    private boolean hasNormal(String name, List<StockProduct> products) {
        return products.stream()
                .anyMatch(sp -> sp.getName().equals(name)
                        && sp.getPromotionType() == PromotionType.NONE);
    }

    private StockProduct createNormalProduct(String name, int price) {
        return new StockProduct(
                name,
                price,
                0,
                PromotionType.NONE
        );
    }


    /**
     * 손님에게 물건을 꺼내주다
     */
    public List<StockProduct> giveProduct(String name, int amount) {
        if (!hasProduct(name)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage());
        }
        List<StockProduct> preferredPromotionProduct = getPreferredPromotionProduct(name, amount);
        applyChangesToStock(preferredPromotionProduct);
        return preferredPromotionProduct;
    }

    private boolean hasProduct(String name) {
        return this.stockProducts.stream()
                .anyMatch(product -> product.isSameProduct(name));
    }

    private List<StockProduct> getPreferredPromotionProduct(String name, int amount) {
        List<StockProduct> result = new ArrayList<>();
        int remainingCount = processPromotionProducts(result, name, amount);
        processNormalProducts(result, name, remainingCount);
        return result;
    }

    private int processPromotionProducts(List<StockProduct> result, String name, int amount) {
        if (!hasPromotionMoreThanOne(name)) {
            return amount;
        }
        StockProduct copyPromotion = createPromotionCopy(name, amount);
        result.add(copyPromotion);
        return amount - copyPromotion.getCount();
    }

    private void processNormalProducts(List<StockProduct> result, String name, int remainingCount) {
        if (hasNormalMoreThanOne(name) && remainingCount > 0) {
            StockProduct copyNormal = createNormalCopy(name, remainingCount);
            result.add(copyNormal);
        }
    }

    private StockProduct createPromotionCopy(String name, int amount) {
        StockProduct promotion = findPromotion(name);
        return copyPromotionProductsWithMinus(promotion, amount);
    }

    private StockProduct createNormalCopy(String name, int amount) {
        StockProduct normal = findNormal(name);
        return copyNormalProductsWithMinus(normal, amount);
    }

    private boolean hasPromotionMoreThanOne(String name) {
        return this.stockProducts.stream()
                .filter(product -> product.isSameProduct(name))
                .filter(StockProduct::isPromotion)
                .anyMatch(StockProduct::hasProduct);
    }

    private StockProduct findPromotion(String name) {
        return this.stockProducts.stream()
                .filter(product -> product.isSameProduct(name))
                .filter(StockProduct::isPromotion)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage()));
    }


    private boolean hasNormalMoreThanOne(String name) {
        return this.stockProducts.stream()
                .filter(product -> product.isSameProduct(name))
                .filter(product -> !product.isPromotion())
                .anyMatch(StockProduct::hasProduct);
    }

    private StockProduct findNormal(String name) {
        return this.stockProducts.stream()
                .filter(product -> product.isSameProduct(name))
                .filter(product -> !product.isPromotion())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage()));
    }


    private StockProduct copyNormalProductsWithMinus(StockProduct normal, int remainingAmount) {
        if (normal.getCount() < remainingAmount) {
            throw new IllegalArgumentException(ErrorMessage.NOT_ENOUGH_PRODUCT.getMessage());
        }
        StockProduct clone = normal.deepCopy();
        clone.updateCount(remainingAmount);
        return clone;
    }

    private StockProduct copyPromotionProductsWithMinus(StockProduct promotionProduct, int amount) {
        int count = promotionProduct.getCount();
        StockProduct clone = promotionProduct.deepCopy();
        if (count > amount) {
            clone.updateCount(amount);
        }
        return clone;
    }

    private void applyChangesToStock(List<StockProduct> preferredPromotionProduct) {
        for (StockProduct givedProduct : preferredPromotionProduct) {
            applyProductChange(givedProduct);
        }
    }

    private void applyProductChange(StockProduct givedProduct) {
        for (StockProduct stockProduct : stockProducts) {
            if (stockProduct.isSameNameAndPromotionType(givedProduct)) {
                stockProduct.minusCount(givedProduct.getCount());
            }
        }
    }

    public List<StockProduct> getStockProducts() {
        return this.stockProducts;
    }
}
