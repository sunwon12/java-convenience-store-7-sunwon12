package store.view;

import java.util.List;
import store.model.product.StockProduct;

public class OutptView {

    private static final String WELCOME = "안녕하세요. W편의점입니다.\n"
            + "현재 보유하고 있는 상품입니다.\n";
    private static final String PRODUCT_INFO_WITH_COUNT = "- %s %,d원 %d개 %s\n"; // 프로모션 있을 때
    private static final String PRODUCT_INFO = "- %s %,d원 재고 없음 %s\n"; //재고 없을 때


    public void printWelcome() {
        System.out.println(WELCOME);
    }
    public void printProductInfo(List<StockProduct> products) {
        for (StockProduct stockProduct : products) {
            if (stockProduct.getCount() == 0) {
                printProductInfoWithOutCount(stockProduct);
            }
            if (stockProduct.getCount() != 0) {
                printProductInfo(stockProduct);
            }
        }
    }

    private void printProductInfoWithOutCount(StockProduct stockProduct) {
        System.out.printf(PRODUCT_INFO,
                stockProduct.getName(),
                stockProduct.getPrice(),
                stockProduct.getPromotionType().getDescription());
    }

    private void printProductInfo(StockProduct stockProduct) {
        System.out.printf(PRODUCT_INFO_WITH_COUNT,
                stockProduct.getName(),
                stockProduct.getPrice(),
                stockProduct.getCount(),
                stockProduct.getPromotionType().getDescription());
    }
}
