package store.model.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.model.CustomFileReader;
import store.model.dto.StockDto;
import store.model.dto.StockDtos;

public class Stocks {
    private Map<ProductName, Stock> stocks;

    public Stocks() {
        this.stocks = new HashMap<>();
    }

    public void initialize(CustomFileReader customFileReader) {
        List<StockDto> stockDtos = customFileReader.loadProducts();
        StockDtos dtos = StockDtos.from(stockDtos);
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
}
