package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.CustomFileReader;
import store.model.ProductDto;
import store.model.PromotionDto;

class CustomFileReaderTest {

    CustomFileReader customFileReader = new CustomFileReader();

    @Test
    @DisplayName("상품 목록 파일을 읽어서 ProductsDto로 변환한다")
    void loadProducts() {
        List<ProductDto> productDtos = customFileReader.loadProducts();

        assertThat(productDtos).hasSize(16);

        assertThat(productDtos.get(0))
                .extracting("name", "price", "quantity", "promotionType")
                .containsExactly("콜라", 1000, 10, "탄산2+1");
    }

    @Test
    @DisplayName("프로모션 목록 파일을 읽어서 PromotionsDto로 변환한다")
    void loadPromotions() {
        List<PromotionDto> promotionDtos = customFileReader.loadPromotions();

        assertThat(promotionDtos).hasSize(3);

        assertThat(promotionDtos.get(0))
                .extracting("name", "buy", "get", "startDate", "endDate")
                .containsExactly("탄산2+1", 2, 1, "2024-01-01", "2024-12-31");
    }
}
