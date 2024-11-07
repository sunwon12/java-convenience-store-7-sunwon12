package store.model.dto;

public record PromotionDto(
        String name,  // 탄산2+1, MD추천상품, 반짝할인
        int buy,      // 2, 1, 1
        int get,      // 1, 1, 1
        String startDate, // 2024-01-01
        String endDate    // 2024-12-31
) {}

