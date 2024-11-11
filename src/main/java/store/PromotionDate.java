package store;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;
import store.model.CustomFileReader;
import store.model.dto.PromotionDto;
import store.model.product.PromotionType;

public class PromotionDate {

    private EnumMap<PromotionType, BaseTime> map;

    public PromotionDate() {
        map = new EnumMap<>(PromotionType.class);
    }

    public void initallize(CustomFileReader customFileReader) {
        List<PromotionDto> promotionDtos = customFileReader.loadPromotions();
        this.map = promotionDtos.stream()
                .collect(Collectors.toMap(
                        promotionDto -> PromotionType.from(promotionDto.name()),
                        promotionDto -> new BaseTime(parseDate(promotionDto.startDate())
                                , parseDate(promotionDto.endDate())),
                        (existing, replacement) -> replacement,
                        () -> new EnumMap<>(PromotionType.class)
                ));
        map.put(PromotionType.NONE, new BaseTime(LocalDate.MIN, LocalDate.MAX));
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date);
    }

    public boolean isBetweenPromotionDate(PromotionType promotionType, LocalDateTime input) {
        return map.get(promotionType).isBetweenDate(input);
    }

    public LocalDate getStartDate(PromotionType promotionType) {
        return map.get(promotionType).startDate();
    }
}
