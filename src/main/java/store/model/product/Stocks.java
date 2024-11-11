package store.model.product;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.model.common.CustomFileReader;
import store.model.common.ErrorMessage;
import store.model.dto.OrderProductInfoRequest;
import store.model.dto.StockDtos;

public class Stocks {

    private Map<ProductName, Stock> stocks;

    public Stocks() {
        this.stocks = new LinkedHashMap<>();
    }

    public void initiallize(CustomFileReader customFileReader) {
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

        return Map.of(name, stock.release(quantity));
    }

    private void validateProduct(ProductName name) {
        if (!hasStock(name)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage());
        }
    }

    public boolean checkEnoughPromotionQuantity(ProductName productName, Quantity quantity) {
        Stock stock = stocks.get(productName);
        return stock.hasEnoughPromotionStock(quantity);
    }

    public boolean hasStock(ProductName productName) {
        return stocks.containsKey(productName);
    }

    public Map<ProductName, Stock> getStocks() {
        return stocks;
    }

    public void addReleasedProductInStocks(Map.Entry<ProductName, ReleasedProduct> subtractProducts) {
        ProductName productName = subtractProducts.getKey();
        Stock stock = stocks.get(productName);
        Stock newStock = stock.add(subtractProducts.getValue());
        stocks.put(productName, newStock);
    }
}
