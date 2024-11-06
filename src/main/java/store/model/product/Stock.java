package store.model.product;

import java.util.ArrayList;
import java.util.List;
import store.model.CustomFileReader;
import store.model.StockProductDto;

public class Stock {

    private final List<StockProduct> stockProducts;

    public Stock() {
        this.stockProducts = new ArrayList<>();
    }

    public Stock(List<StockProduct> stockProducts) {
         this.stockProducts = new ArrayList<>(stockProducts);
    }
//
//    public Stock addProduct(StockProduct stockProduct) {
//        stockProducts.add(stockProduct);
//        return new Stock(stockProducts);
//    }
//
//    public int getProductKindCount() {
//        return this.stockProducts.size();
//    }

    protected List<StockProduct> getStockProducts() {
        return this.stockProducts;
    }

    public Stock initializeStock() {
        List<StockProductDto> stockProductDtos = new CustomFileReader().loadProducts();
        List<StockProduct> stockProducts = stockProductDtos.stream()
                .map(stockProductDto ->
                        new StockProduct(new Product(stockProductDto.productId(), stockProductDto.promotionType()),
                                stockProductDto.count())
                )
                .toList();

        return new Stock(stockProducts);
    }
}
