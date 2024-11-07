package store.view;

import java.util.List;
import store.model.product.Product;

public class OutptView {

    private static final String WELCOME = "안녕하세요. W편의점입니다.\n"
            + "현재 보유하고 있는 상품입니다.\n";
    private static final String PRODUCT_INFO_WITH_COUNT = "- %s %,d원 %d개 %s\n"; // 프로모션 있을 때
    private static final String PRODUCT_INFO = "- %s %,d원 재고 없음 %s\n"; //재고 없을 때


    public void printWelcome() {
        System.out.println(WELCOME);
    }
    public void printProductInfo(List<Product> products) {
        for (Product product : products) {
            if (product.getCount() == 0) {
                printProductInfoWithOutCount(product);
            }
            if (product.getCount() != 0) {
                printProductInfo(product);
            }
        }
    }

    private void printProductInfoWithOutCount(Product product) {
        System.out.printf(PRODUCT_INFO,
                product.getName(),
                product.getPrice(),
                product.getPromotionType().getDescription());
    }

    private void printProductInfo(Product product) {
        System.out.printf(PRODUCT_INFO_WITH_COUNT,
                product.getName(),
                product.getPrice(),
                product.getCount(),
                product.getPromotionType().getDescription());
    }
}
