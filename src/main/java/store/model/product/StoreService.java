package store.model.product;


import store.model.CustomFileReader;

public class StoreService {

    private final Stocks stocks;

    public StoreService() {
        this.stocks = new Stocks();
    }

    public void initiallizeStocks() {
        stocks.initialize(new CustomFileReader());
    }

    public Stocks getPrintStocks() {
        return stocks;
    }
}
