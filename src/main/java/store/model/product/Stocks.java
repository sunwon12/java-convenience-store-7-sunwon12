package store.model.product;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.model.CustomFileReader;
import store.model.ErrorMessage;
import store.model.dto.OrderProductInfoRequest;
import store.model.dto.StockDtos;

public class Stocks {

    private Map<ProductName, Stock> stocks;

    public Stocks() {
        this.stocks = new LinkedHashMap<>();
    }

    public void initialize(CustomFileReader customFileReader) {
        StockDtos dtos = customFileReader.loadProducts();
        dtos.groupedProducts()
                .keySet()
                .forEach(name -> stocks.put(
                        new ProductName(name),
                        dtos.toStock(name)
                ));
    }

    public Map<ProductName, ReleasedProduct> selectProduct(List<OrderProductInfoRequest> requests) {
        Map<ProductName, ReleasedProduct> result = new LinkedHashMap<>();

        requests.stream()
                .map(request -> selectProduct(request.name(), request.amount()))
                .forEach(result::putAll);

        return result;
    }

    public Map<ProductName, ReleasedProduct> selectProduct(ProductName name, Quantity quantity) {
        validateProduct(name);
        Stock stock = stocks.get(name);
        validateQuantity(stock, quantity);

        return Map.of(name, stock.release(quantity));
    }

    private void validateProduct(ProductName name) {
        if (!hasStock(name)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage());
        }
    }

    private void validateQuantity(Stock stock, Quantity quantity) {
        if (!stock.hasEnoughStock(quantity)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_ENOUGH_PRODUCT.getMessage());
        }
    }

    public boolean validateQuantity(ProductName productName, Quantity quantity) {
        Stock stock = stocks.get(productName);
        return stock.hasEnoughStock(quantity);

    }

    public boolean hasStock(ProductName productName) {
        return stocks.containsKey(productName);
    }

    public Map<ProductName, Stock> getStocks() {
        return stocks;
    }

//    public void add(Map<ProductName, Quantity> nonPromotions) {
//        nonPromotions.entrySet().stream()
//                .forEach(entry -> {
//                    Stock stock = stocks.get(entry.getKey());
//                    stock.
//                });
//    }
}
