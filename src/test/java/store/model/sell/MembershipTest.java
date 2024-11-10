package store.model.sell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.product.Money;

class MembershipTest {

    @DisplayName("맴버십 할인을 해준다")
    @Test
    void test1() {
        Membership membership = new Membership();
        Money actual = membership.discount(new Money(10_000));

        assertEquals(new Money(7_000), actual);
    }
}
