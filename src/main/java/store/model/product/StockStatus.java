package store.model.product;

import store.model.ErrorMessage;

class StockStatus {

    private final Quantity promotionalQuantity;
    private final Quantity normalQuantity;
    private final Quantity requestedQuantity;

    public StockStatus(Quantity promotionalQuantity, Quantity normalQuantity, Quantity requestedQuantity) {
        this.promotionalQuantity = promotionalQuantity;
        this.normalQuantity = normalQuantity;
        this.requestedQuantity = requestedQuantity;
    }

    public boolean hasEnoughStock() {
        return getTotalQuantity().isGreaterThanOrEqual(requestedQuantity);
    }

    private Quantity getTotalQuantity() {
        return promotionalQuantity.add(normalQuantity);
    }

    public Quantity getPromotionalAvailable() {
        if (promotionalQuantity.isLessThan(requestedQuantity)) {
            return promotionalQuantity;
        }
        return requestedQuantity;
    }

    public Quantity getNormalRequired() {
        if (!hasInsufficientPromotionalStock()) {
            return Quantity.ZERO;
        }
        return requestedQuantity.subtract(promotionalQuantity);
    }

    public boolean hasEnoughPromotionStock() {
        return promotionalQuantity.isGreaterThanOrEqual(requestedQuantity);
    }

    public boolean hasInsufficientPromotionalStock() {
        return promotionalQuantity.isLessThan(requestedQuantity);
    }

    public void validateStock() {
        if (!hasEnoughStock()) {
            throw new IllegalArgumentException(ErrorMessage.NOT_ENOUGH_PRODUCT.getMessage());
        }
    }
}
