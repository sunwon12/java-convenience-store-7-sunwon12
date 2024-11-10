package store.model.product;

public class Money {

    public static final Money ZERO = new Money(0);

    private final int price;

    public Money(int price) {
        this.price = price;
    }

    public Money applyRate(Double rate) {
        return new Money((int)(price * rate));
    }

    public Money add(Money money) {
        return new Money(price + money.price);
    }

    public Money multiply(int multiplier) {
        return new Money(this.price * multiplier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return price == money.price;
    }

    public int getValue() {
        return price;
    }

    public Money subtract(Money promotionDiscount) {
        return new Money(price - promotionDiscount.price);
    }
}
