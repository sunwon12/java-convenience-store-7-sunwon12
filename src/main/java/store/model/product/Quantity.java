package store.model.product;

import java.util.Objects;
import store.model.common.ErrorMessage;

public class Quantity {

    public static final Quantity ZERO = new Quantity(0);
    private final int value;

    public Quantity(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_QUANTITY.getMessage());
        }
    }

    public static Quantity from(int value) {
        if (value == 0) {
            return ZERO;
        }
        return new Quantity(value);
    }

    public Quantity add(Quantity other) {
        return new Quantity(this.value + other.value);
    }

    public Quantity subtract(Quantity other) {
        return new Quantity(this.value - other.value);
    }

    public boolean isLessThan(Quantity other) {
        return this.value < other.value;
    }

    public boolean isGreaterThanOrEqual(Quantity other) {
        return this.value >= other.value;
    }

    public boolean isZero() {
        return this.value == 0;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quantity)) {
            return false;
        }
        Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        if(value == 0) {
            return "재고 없음";
        }
        return String.valueOf(value) + "개";
    }

    public Quantity remainder(int divisor) {
        return new Quantity(this.value % divisor);
    }
}
