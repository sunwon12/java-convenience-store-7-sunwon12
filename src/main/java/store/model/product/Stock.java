package store.model.product;

import java.util.ArrayList;
import java.util.List;
import store.model.CustomFileReader;
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
}
