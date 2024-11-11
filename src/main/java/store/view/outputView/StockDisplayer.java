package store.view.outputView;

import store.model.product.ProductName;
import store.model.product.Stock;

public class StockDisplayer {

    private static final String PRODUCT_PREFIX = "- ";

    public void display(ProductName productName, Stock stock) {
        StringBuilder sb = new StringBuilder();

        appendPromotiolInfo(sb, productName, stock);
        appendNormalInfo(sb, productName, stock);

        System.out.print(sb.toString());
    }

    private void appendPromotiolInfo(StringBuilder sb, ProductName productName, Stock stock) {
        if (!stock.hasPromotion() && !stock.hadPromotion()) {
            return;
        }
        sb.append(createBasicInfo(productName));
        sb.append(addPromotionalStatus(stock));
    }

    private void appendNormalInfo(StringBuilder sb, ProductName productName, Stock stock) {
        sb.append(createBasicInfo(productName))
                .append(addNormalStatus(stock));
    }

    private StringBuilder createBasicInfo(ProductName productName) {
        return new StringBuilder()
                .append(PRODUCT_PREFIX)
                .append(productName.name())
                .append(" ");
    }

    private StringBuilder addPromotionalStatus(Stock stock) {
        return new StringBuilder()
                .append(addPrice(stock))
                .append(stock.getPromotionalQuantity())
                .append(" ")
                .append(stock.getPromotionType())
                .append("\n");
    }

    private StringBuilder addNormalStatus(Stock stock) {
        return new StringBuilder()
                .append(addPrice(stock))
                .append(stock.getNormalQuantity())
                .append("\n");
    }

    private StringBuilder addPrice(Stock stock) {
        return new StringBuilder()
                .append(String.format("%,d원 ", stock.getPrice()));
    }
}

