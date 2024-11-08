package store.model.product;

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

    public boolean hasInsufficientPromotionalStock() {
        return promotionalQuantity.isLessThan(requestedQuantity);
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

    private Quantity getTotalQuantity() {
        return promotionalQuantity.add(normalQuantity);
    }

    private void validateStock() {
        if (!hasEnoughStock()) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
    }
}
