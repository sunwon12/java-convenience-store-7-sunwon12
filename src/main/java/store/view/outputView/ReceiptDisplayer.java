package store.view.outputView;

import store.model.sell.Receipt;

public class ReceiptDisplayer {
    private static final String TITLE_LINE = "==============W 편의점================";
    private static final String PROMOTION_LINE = "=============증      정===============";
    private static final String BOTTOM_LINE = "====================================";

    private static final String NAME_COLUMN_SPACE = "\t\t\t\t";  // 상품명 뒤 공백
    private static final String QUANTITY_COLUMN_SPACE = "\t\t  "; // 수량 뒤 공백
    private static final String PRICE_COLUMN_SPACE = "\n";        // 금액 뒤 공백
    private static final String SUMMARY_SPACE = "\t\t\t\t\t\t  "; // 합계 항목 공백

    public void display(Receipt receipt) {
        StringBuilder sb = new StringBuilder();
        appendHeader(sb);
        int maxNameLength = calculateMaxNameLength(receipt);
        appendPurchaseList(sb, receipt, maxNameLength);
        appendPromotions(sb, receipt, maxNameLength);
        appendTotalAmounts(sb, receipt, maxNameLength);
        System.out.println(sb);
    }

    private int calculateMaxNameLength(Receipt receipt) {
        int maxProductLength = receipt.getProducts().keySet().stream()
                .mapToInt(name -> name.getName().length())
                .max()
                .orElse(0);

        int maxPromotionLength = receipt.getPromotionDiscountPrice().keySet().stream()
                .mapToInt(name -> name.getName().length())
                .max()
                .orElse(0);

        return Math.max(maxProductLength, maxPromotionLength);
    }

    private void appendHeader(StringBuilder sb) {
        sb.append("\n").append(TITLE_LINE).append(PRICE_COLUMN_SPACE);
        sb.append("상품명").append(NAME_COLUMN_SPACE)
                .append("수량").append(QUANTITY_COLUMN_SPACE)
                .append("금액").append(PRICE_COLUMN_SPACE);
    }

    private void appendPurchaseList(StringBuilder sb, Receipt receipt, int maxNameLength) {
        receipt.getProducts().forEach((name, product) -> {
            sb.append(String.format("%-" + maxNameLength + "s", name.getName()))
                    .append(NAME_COLUMN_SPACE)
                    .append(product.getTotalQuantity().getValue())
                    .append(QUANTITY_COLUMN_SPACE)
                    .append(String.format("%,d", product.getTotalMoney().getValue()))
                    .append(PRICE_COLUMN_SPACE);
        });
    }

    private void appendPromotions(StringBuilder sb, Receipt receipt, int maxNameLength) {
        sb.append(PROMOTION_LINE).append(PRICE_COLUMN_SPACE);
        receipt.getPromotionDiscountPrice().forEach((name, quantity) -> {
            if (!quantity.isZero()) {
                sb.append(String.format("%-" + maxNameLength + "s", name.getName()))
                        .append(NAME_COLUMN_SPACE)
                        .append(quantity.getValue())
                        .append(PRICE_COLUMN_SPACE);
            }
        });
    }

    private void appendTotalAmounts(StringBuilder sb, Receipt receipt, int maxNameLength) {
        sb.append(BOTTOM_LINE).append(PRICE_COLUMN_SPACE);
        appendTotalPrice(sb, receipt, maxNameLength);
        appendDiscounts(sb, receipt, maxNameLength);
        appendFinalPrice(sb, receipt, maxNameLength);
    }

    private void appendTotalPrice(StringBuilder sb, Receipt receipt, int maxNameLength) {
        int totalQuantity = calculateTotalAmount(receipt);

        sb.append(String.format("%-" + maxNameLength + "s", "총구매액"))
                .append(NAME_COLUMN_SPACE)
                .append(totalQuantity)
                .append(QUANTITY_COLUMN_SPACE)
                .append(String.format("%,d", receipt.getTotalPrice().getValue()))
                .append(PRICE_COLUMN_SPACE);
    }

    private void appendDiscounts(StringBuilder sb, Receipt receipt, int maxNameLength) {
        int promotionDiscount = calculatePromotionDiscount(receipt);
        sb.append("행사할인").append(SUMMARY_SPACE)
                .append(String.format("-%,d", promotionDiscount))
                .append(PRICE_COLUMN_SPACE);
        sb.append("멤버십할인").append(SUMMARY_SPACE)
                .append(String.format("-%,d", receipt.getMembershipDiscountPrice().getValue()))
                .append(PRICE_COLUMN_SPACE);
    }

    private void appendFinalPrice(StringBuilder sb, Receipt receipt, int maxNameLength) {
        int finalPrice = calculateFinalPrice(receipt);
        sb.append("내실돈").append(SUMMARY_SPACE)
                .append(String.format("%,d", finalPrice))
                .append(PRICE_COLUMN_SPACE);
        ;
    }

    private static int calculateTotalAmount(Receipt receipt) {
        return receipt.getProducts().values().stream()
                .mapToInt(product -> product.getTotalQuantity().getValue())
                .sum();
    }

    private int calculatePromotionDiscount(Receipt receipt) {
        return receipt.getPromotionDiscountPrice().entrySet().stream()
                .mapToInt(entry -> entry.getValue().getValue() *
                        receipt.getProducts().get(entry.getKey()).getTotalMoney().getValue())
                .sum();
    }

    private int calculateFinalPrice(Receipt receipt) {
        return receipt.getTotalPrice().getValue()
                - calculatePromotionDiscount(receipt)
                - receipt.getMembershipDiscountPrice().getValue();
    }
}
