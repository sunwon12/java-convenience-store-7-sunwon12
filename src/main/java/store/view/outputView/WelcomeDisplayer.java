package store.view.outputView;

public class WelcomeDisplayer {

    private static final String WELCOME_MESSAGE = "\n안녕하세요. W편의점입니다.";
    private static final String CURRENT_STOCK_MESSAGE = "현재 보유하고 있는 상품입니다.\n";

    public void display() {
        System.out.println(WELCOME_MESSAGE);
        System.out.println(CURRENT_STOCK_MESSAGE);
    }
}
