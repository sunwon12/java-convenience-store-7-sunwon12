package store.view.outputView;

import store.model.product.ProductName;
import store.model.product.Stock;

public class StockDisplayer {

    private static final String PRODUCT_PREFIX = "- ";

    public void display(ProductName productName, Stock stock) {
        StringBuilder sb = new StringBuilder();
        if (stock.hasPromotion()) {
            sb.append(createBasicInfo(productName, stock));
            sb.append(addPromotionalStatus(stock));
        }
        sb.append(createBasicInfo(productName, stock));
        sb.append(addNormalStatus(stock));
        System.out.print(sb.toString());
    }

    private StringBuilder createBasicInfo(ProductName productName, Stock stock) {
        return new StringBuilder()
                .append(PRODUCT_PREFIX)
                .append(productName.getName())
                .append(" ");
    }

    private StringBuilder addPromotionalStatus(Stock stock) {
        return new StringBuilder()
                .append(stock.getPromotionalQuantity())
                .append(" ")
                .append(stock.getPromotionType())
                .append("\n");
    }

    private StringBuilder addNormalStatus(Stock stock) {
        return new StringBuilder()
                .append(stock.getNormalQuantity())
                .append("\n");
    }
}

