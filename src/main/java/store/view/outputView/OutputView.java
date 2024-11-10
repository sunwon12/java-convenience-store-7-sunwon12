package store.view.outputView;

import store.model.product.Stocks;
import store.model.sell.Receipt;

public class OutputView {

    private final WelcomeDisplayer welcomeDisplayer;
    private final StocksDisplayer stocksDisplayer;
    private final ReceiptDisplayer receiptDisplayer;

    public OutputView() {
        this.welcomeDisplayer = new WelcomeDisplayer();
        this.stocksDisplayer = new StocksDisplayer(new StockDisplayer());
        this.receiptDisplayer = new ReceiptDisplayer();
    }

    public void showStocks(Stocks stocks) {
        welcomeDisplayer.display();
        stocksDisplayer.display(stocks);
    }

    public void showReceipt(Receipt receipt) {
        receiptDisplayer.display(receipt);
    }
}
