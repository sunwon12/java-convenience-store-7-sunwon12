package store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.product.Money;
import store.model.product.Product;
import store.model.product.ProductName;
import store.model.product.PromotionType;
import store.model.product.Quantity;
import store.model.product.ReleasedProduct;

class ShoppingCartTest {

    @DisplayName("물건을 담는다")
    @Test
    void test1() {
        ShoppingCart cart = new ShoppingCart();
        Map<ProductName, ReleasedProduct> products = new HashMap<>();
        products.put(new ProductName("Product A"), new ReleasedProduct(null, new Quantity(5), new Quantity(10), null));
        products.put(new ProductName("Product B"), new ReleasedProduct(null, new Quantity(3), new Quantity(7), null));

        cart.add(products);

        assertEquals(2, cart.getProducts().size());
        assertTrue(cart.getProducts().containsKey(new ProductName("Product A")));
        assertTrue(cart.getProducts().containsKey(new ProductName("Product B")));
    }

    @DisplayName("기존에 동일 물건이 있어도 정상적으로 추가로 담는다")
    @Test
    void test2() {
        ShoppingCart cart = new ShoppingCart();
        Map<ProductName, ReleasedProduct> firstProducts = new HashMap<>();
        firstProducts.put(new ProductName("Product A"),
                new ReleasedProduct(null, new Quantity(5), new Quantity(10), PromotionType.NONE));
        cart.add(firstProducts);

        Map<ProductName, ReleasedProduct> secondProducts = new HashMap<>();
        secondProducts.put(new ProductName("Product A"),
                new ReleasedProduct(null, new Quantity(2), new Quantity(3), PromotionType.NONE));
        cart.add(secondProducts);

        assertEquals(new Quantity(7), cart.getProducts().get(new ProductName("Product A")).promotionQuantity());
        assertEquals(new Quantity(13), cart.getProducts().get(new ProductName("Product A")).normalQuantity());
    }

    @DisplayName("몇 개 프로모션 적용을 받을 수 있는지 확인한다")
    @Test
    void test3() {
        ShoppingCart cart = new ShoppingCart();
        Map<ProductName, ReleasedProduct> products = new HashMap<>();
        ProductName productName = new ProductName("콜라");
        products.put(productName, new ReleasedProduct(new Product(productName, new Money(1000)), new Quantity(5), new Quantity(10), PromotionType.TWO_PLUS_ONE));
        cart.add(products);

        Map<ProductName, ReleasedProduct> cantPromotion = cart.calculateNonPromotionQuantity();

        assertTrue(cantPromotion.containsKey(productName));
        assertEquals(new Quantity(12), cantPromotion.get(productName).getTotalQuantity());
    }
}
