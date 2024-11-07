package store.view;

public class OutptView {

    private static final String WELCOME = "안녕하세요. W편의점입니다.\n"
            + "현재 보유하고 있는 상품입니다.\n";
    private static final String PRODUCT_INFO_WITH_COUNT = "- %s %,d원 %d개 %s\n"; // 프로모션 있을 때
    private static final String PRODUCT_INFO = "- %s %,d원 재고 없음 %s\n"; //재고 없을 때
}
