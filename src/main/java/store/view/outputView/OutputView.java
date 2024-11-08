package store.view.outputView;

import store.model.product.Stocks;

public class OutputView {

    private final WelcomeDisplayer welcomeDisplayer;
    private final StocksDisplayer stocksDisplayer;

    public OutputView() {
        this.welcomeDisplayer = new WelcomeDisplayer();
        this.stocksDisplayer = new StocksDisplayer(new StockDisplayer());
    }

    public void showStocks(Stocks stocks) {
        welcomeDisplayer.display();
        stocksDisplayer.display(stocks);
    }
}
