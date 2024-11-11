package store.view;

import store.model.common.ErrorMessage;

public class SimpleValidator {

    private static final String INPUT_PRODUCT_NAME_COUNT_FORMAT = "^\\[\\D+-\\d+\\](\\s*,\\s*\\[\\D+-\\d\\])*$";
    private static final String YES = "Y";
    private static final String NO = "N";

    public void validateProductNameAndCount(String input) {
        if (!input.matches(INPUT_PRODUCT_NAME_COUNT_FORMAT)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PRODUCT_NAME_AND_COUNT.getMessage());
        }
    }

    public void validateYesOrNo(String input) {
        if (!(input.matches(YES) || input.matches(NO))) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ETC.getMessage());
        }
    }
}
