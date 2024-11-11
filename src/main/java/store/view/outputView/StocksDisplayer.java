package store.view.outputView;

import store.model.product.Stocks;

public class StocksDisplayer {

    private StockDisplayer stockDisplayer;

    public StocksDisplayer(StockDisplayer stockDisplayer) {
        this.stockDisplayer = stockDisplayer;
    }

    public void display(Stocks stocks) {
        stocks.getStocks().entrySet().stream()
                .forEach(entry -> stockDisplayer.display(entry.getKey(), entry.getValue()));
    }
}
