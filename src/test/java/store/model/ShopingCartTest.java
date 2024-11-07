package store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.Stock;

class ShopingCartTest {

    Stock stock;
    ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        stock = new Stock();
        stock.initializeStock();
        shoppingCart = new ShoppingCart(stock);
    }

    @DisplayName("쇼핑카트에 상품을 담는다")
    @Test
    void test1() {
        OrderProductInfoRequest collaRequest = new OrderProductInfoRequest("콜라", 1);
        OrderProductInfoRequest orangeRequest = new OrderProductInfoRequest("사이다", 1);
        List<OrderProductInfoRequest> reqeusts = List.of(collaRequest, orangeRequest);

        ShoppingCart firstCart = shoppingCart.addProducts(reqeusts);

        assertEquals(collaRequest.name(), firstCart.getProducts().getFirst().getName());
    }

    @DisplayName("놓친 프로모션을 챙긴다")
    @Test
    void test2() {
        OrderProductInfoRequest collaRequest = new OrderProductInfoRequest("오렌지주스", 1);
        List<OrderProductInfoRequest> reqeusts = List.of(collaRequest);
        ShoppingCart fistCart = shoppingCart.addProducts(reqeusts);

        ShoppingCart cartWithMissing = fistCart.takeMissingPromotion();

        assertEquals("오렌지주스", cartWithMissing.getProducts().getFirst().getName());
        assertEquals(2, cartWithMissing.getProducts().getFirst().getCount());
    }

    @DisplayName("놓친 프로모션이 여러 종류일 때도 챙긴다")
    @Test
    void test3() {
        OrderProductInfoRequest collaRequest = new OrderProductInfoRequest("오렌지주스", 1);
        OrderProductInfoRequest orangeRequest = new OrderProductInfoRequest("감자칩", 1);
        List<OrderProductInfoRequest> reqeusts = List.of(collaRequest, orangeRequest);
        ShoppingCart fistCart = shoppingCart.addProducts(reqeusts);

        ShoppingCart cartWithMissing = fistCart.takeMissingPromotion();

        assertEquals("오렌지주스", cartWithMissing.getProducts().getFirst().getName());
        assertEquals(2, cartWithMissing.getProducts().getFirst().getCount());
        assertEquals("감자칩", cartWithMissing.getProducts().get(1).getName());
        assertEquals(2, cartWithMissing.getProducts().getFirst().getCount());

    }

}
