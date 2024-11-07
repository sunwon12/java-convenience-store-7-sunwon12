package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.CustomFileReader;
import store.model.dto.PromotionDto;
import store.model.dto.StockProductDto;

class CustomFileReaderTest {

    CustomFileReader customFileReader = new CustomFileReader();

    @Test
    @DisplayName("상품 목록 파일을 읽어서 ProductsDto로 변환한다")
    void loadProducts() {
        List<StockProductDto> stockProductDtos = customFileReader.loadProducts();
        assertThat(stockProductDtos).hasSize(16);
    }

    @Test
    @DisplayName("프로모션 목록 파일을 읽어서 PromotionsDto로 변환한다")
    void loadPromotions() {
        List<PromotionDto> promotionDtos = customFileReader.loadPromotions();
        assertThat(promotionDtos).hasSize(3);
    }
}
