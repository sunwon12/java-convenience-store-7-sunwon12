package store.model.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

    @DisplayName("재고를 초기화 한다")
    @Test
    void test1() {
        ProductId productId = new ProductId("콜라", 1000);
        Product product = new Product(productId, PromotionType.from("탄산2+1"));
        StockProduct stockProduct = new StockProduct(product, 10);

        Stock stock = new Stock().initializeStock();
        assertEquals(stockProduct, stock.getStockProducts().get(0));

    }

//    @DisplayName("재고를 여러 개 추가한다")
//    @Test
//    void test11() {
//        ProductId productId = new ProductId("iceCream", 1_000);
//        Product product1 = new Product(productId, PromotionType.MD_RECOMMENDED);
//        Product product2 = new Product(productId, PromotionType.NONE);
//        StockProduct stockProduct1 = new StockProduct(product1, 1);
//        StockProduct stockProduct2 = new StockProduct(product2, 1);
//
//        Stock stock = new Stock(List.of(stockProduct1, stockProduct2));
//
//        assertEquals(2, stock.getProductKindCount());
//    }
//
//
//    @DisplayName("재고를 추가한다")
//    @Test
//    void test1() {
//        ProductId productId = new ProductId("iceCream", 1_000);
//        Product product1 = new Product(productId, PromotionType.MD_RECOMMENDED);
//        StockProduct stockProduct = new StockProduct(product1, 1);
//        Stock stock = new Stock();
//
//        Stock stockWithProduct = stock.addProduct(stockProduct);
//
//        assertEquals(1, stockWithProduct.getProductKindCount());
//    }
//
//    @DisplayName("재고 목록을 반환한다")
//    @Test
//    void test2() {
//        ProductId productId = new ProductId("iceCream", 1_000);
//        Product product1 = new Product(productId, PromotionType.MD_RECOMMENDED);
//        StockProduct stockProduct = new StockProduct(product1, 1);
//        Stock stock = new Stock();
//        Stock stockWithProduct = stock.addProduct(stockProduct);
//
//        List<StockProduct> stockProducts = stockWithProduct.getStockProducts();
//
//        assertEquals(List.of(stockProduct), stockProducts);
//    }
}
