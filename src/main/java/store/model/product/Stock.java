package store.model.product;

import java.util.ArrayList;
import java.util.List;
import store.model.CustomFileReader;
import store.model.StockProductDto;

public class Stock {

   private List<StockProduct> stockProducts;

    public Stock() {
        this.stockProducts = new ArrayList<>();
    }

    public Stock(List<StockProduct> stockProducts) {
        this.stockProducts = addNormalProducts(stockProducts);
    }

    private List<StockProduct> addNormalProducts(List<StockProduct> originalProducts) {
        List<StockProduct> result = new ArrayList<>(originalProducts);

        for (int i = 0; i < originalProducts.size(); i++) {
            StockProduct current = originalProducts.get(i);
            if (shouldAddNormal(current, originalProducts)) {
                result.add(i + 1, createNormalVersionProduct(current.getName(), current.getPrice()));
            }
        }

        return result;
    }

    private boolean shouldAddNormal(StockProduct product, List<StockProduct> allProducts) {
        return product.getPromotionType() != PromotionType.NONE
                && !hasNormal(product.getName(), product.getPrice(), allProducts);
    }

    private boolean hasNormal(String name, int price, List<StockProduct> products) {
        return products.stream()
                .anyMatch(sp -> sp.getName().equals(name)
                        && sp.getPrice() == price
                        && sp.getPromotionType() == PromotionType.NONE);
    }

    private StockProduct createNormalVersionProduct(String name, int price) {
        return new StockProduct(
                name,
                price,
                0,
                PromotionType.NONE
        );
    }

    public Stock initializeStock() {
        List<StockProductDto> stockProductDtos = new CustomFileReader().loadProducts();

        List<StockProduct> stockProducts = new ArrayList<>();
        for (StockProductDto dto : stockProductDtos) {
            StockProduct stockProduct = new StockProduct(
                    dto.name(), dto.price(), dto.count(), dto.promotionType());
            stockProducts.add(stockProduct);
        }
        return new Stock(stockProducts);
    }

    public List<StockProduct> getStockProducts() {
        return this.stockProducts;
    }
}
