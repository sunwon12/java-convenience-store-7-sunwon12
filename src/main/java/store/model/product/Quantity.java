package store.model.product;

import java.util.Objects;

public class Quantity {
    public static final Quantity ZERO = new Quantity(0);
    private final int value;

    public Quantity(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("수량은 0보다 작을 수 없습니다.");
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

    public Quantity multiply(int multiplier) {
        return new Quantity(this.value * multiplier);
    }

    public boolean isLessThan(Quantity other) {
        return this.value < other.value;
    }

    public boolean isGreaterThan(Quantity other) {
        return this.value > other.value;
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

    public Quantity min(Quantity other) {
        return this.isLessThan(other) ? this : other;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quantity)) return false;
        Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public Quantity remainder(int divisor) {
        return new Quantity(this.value % divisor);
    }

    public Quantity divide(int divisor) {
        return new Quantity(this.value / divisor);
    }

    public boolean isMultipleOf(int number) {
        return this.value % number == 0;
    }
}
