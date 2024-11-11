package store.model;

public enum ErrorMessage {
    //    존재하지 않는 상품을 입력한 경우: [ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.
//    구매 수량이 재고 수량을 초과한 경우: [ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.
    INVALID_PRODUCT_NAME_AND_COUNT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_ETC("잘못된 입력입니다. 다시 입력해 주세요."),
    NOT_FOUND_FILE_PATH("파일 경로를 찾을 수 없습니다."),
    FAILED_LOAD_PRODUCT_FILE("상품 목록을 불러오는데 실패했습니다."),
    FAILED_LOAD_PROMOTION_FILE("프로모션 목록을 불러오는데 실패했습니다."),
    INVALID_FILE_PRODUCT_FORMAT("상품 목록 파일의 형식이 올바르지 않습니다."),
    INVALID_FILE_PROMOTION_FORMAT("프로모션 목록 파일의 형식이 올바르지 않습니다."),
    NOT_FOUND_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    NOT_ENOUGH_PRODUCT("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_QUANTITY("수량은 0보다 작을 수 없습니다.");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }
}
