package store.model.product;

import java.util.LinkedHashMap;
import java.util.Map;
import store.model.CustomFileReader;
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

    public boolean hasStock(ProductName productName) {
        return stocks.containsKey(productName);
    }

    public Map<ProductName, Stock> getStocks() {
        return stocks;
    }
}
