package store.view.InputView;

import static camp.nextstep.edu.missionutils.Console.readLine;

import java.util.ArrayList;
import java.util.List;
import store.model.dto.OrderProductInfoRequest;
import store.view.SimpleValidator;

public class InputView {

    private static final String BRACKET = "[\\[\\]]";
    private static final String REQUEST_PRODUCT_NAME_COUNT = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String REQUEST_MISSING_PROMOTION = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n";
    private static final String REQUEST_PURCHASING_WITHOUT_PROMOTION = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n";
    private static final String REQUEST_USING_MEMBERSHIP = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String REQUEST_REORDER = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    private final SimpleValidator validator;

    public InputView(SimpleValidator validator) {
        this.validator = validator;
    }

    public List<OrderProductInfoRequest> readProductNameAndCount() {
        System.out.println(REQUEST_PRODUCT_NAME_COUNT);
        String input = readLine();
        validator.validateProductNameAndCount(input);
        return parseProductNameAndCount(input);
    }

    private List<OrderProductInfoRequest> parseProductNameAndCount(String input) {
        List<OrderProductInfoRequest> requests = new ArrayList<>();
        String[] items = splitByComma(input);

        for (String item : items) {
            requests.add(createOrderRequest(item));
        }

        return requests;
    }

    private String[] splitByComma(String input) {
        return input.split(",");
    }

    private OrderProductInfoRequest createOrderRequest(String item) {
        String cleanItem = removeSquareBrackets(item);
        return splitNameAndQuantity(cleanItem);
    }

    private String removeSquareBrackets(String item) {
        return item.trim().replaceAll(BRACKET, "");
    }

    private OrderProductInfoRequest splitNameAndQuantity(String cleanItem) {
        String[] parts = cleanItem.split("-");
        String name = parts[0];
        int quantity = Integer.parseInt(parts[1]);
        return new OrderProductInfoRequest(name, quantity);
    }

    public String readMissingPromotionResponse(String productName, int quantity) {
        System.out.printf(REQUEST_MISSING_PROMOTION, productName, quantity);
        return readLine();
    }

    public String readPurchaseWithoutPromotionResponse(String productName, int quantity) {
        System.out.printf(REQUEST_PURCHASING_WITHOUT_PROMOTION, productName, quantity);
        return readLine();
    }

    public String readUsingMembershipResponse() {
        System.out.println(REQUEST_USING_MEMBERSHIP);
        return readLine();
    }

    public String readReorderResponse() {
        System.out.println(REQUEST_REORDER);
        return readLine();
    }
}
