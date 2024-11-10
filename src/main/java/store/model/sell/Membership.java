package store.model.sell;

import store.model.product.Money;

public class Membership {

    private static final double DISCOUNT_RATE = 0.3;

    public Money discount(Money money) {
        return money.applyRate(1-DISCOUNT_RATE);
    }
}
