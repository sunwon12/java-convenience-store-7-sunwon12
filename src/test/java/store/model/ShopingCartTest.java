package store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.dto.OrderProductInfoRequest;
import store.model.product.Stock;

class ShopingCartTest {

    @DisplayName("쇼핑카트에 상품을 담는다")
    @Test
    void test1() {
        OrderProductInfoRequest collaRequest = new OrderProductInfoRequest("콜라", 1);
        OrderProductInfoRequest orangeRequest = new OrderProductInfoRequest("사이다", 1);
        List<OrderProductInfoRequest> reqeusts = List.of(collaRequest, orangeRequest);
        Stock stock = new Stock();
        stock.initializeStock();
        ShoppingCart shoppingCart = new ShoppingCart(stock);

        shoppingCart.addProducts(reqeusts);

        assertEquals(collaRequest.name(), shoppingCart.getProducts().get(0).getName());
    }

}
