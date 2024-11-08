package store.model.dto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.model.product.Money;
import store.model.product.Product;
import store.model.product.ProductName;
import store.model.product.PromotionType;
import store.model.product.Quantity;
import store.model.product.Stock;

public record StockDtos(Map<String, List<StockDto>> groupedProducts) {
    public static StockDtos from(List<StockDto> dtos) {
        return new StockDtos(dtos.stream()
                .collect(Collectors.groupingBy(
                        StockDto::name,
                        () -> new LinkedHashMap<>(),
                        Collectors.toUnmodifiableList()
                )));
    }

    public Stock toStock(String name) {
        List<StockDto> dtos = groupedProducts.get(name);
        StockDto promotionalDto = findPromotionalDto(dtos);
        StockDto normalDto = findNormalDto(dtos);
        ProductName productName = new ProductName(name);
        Money price = createPrice(promotionalDto, normalDto);
        Quantity promotionalQuantity = createPromotionalQuantity(promotionalDto);
        Quantity normalQuantity = createNormalQuantity(normalDto);
        PromotionType promotionType = createPromotionType(promotionalDto);
        return new Stock(new Product(productName, price), promotionalQuantity, normalQuantity, promotionType);
    }

    private Money createPrice(StockDto promotionalDto, StockDto normalDto) {
        if (promotionalDto != null) {
            return new Money(promotionalDto.price());
        }
        return new Money(normalDto.price());
    }

    private Quantity createPromotionalQuantity(StockDto promotionalDto) {
        if (promotionalDto != null) {
            return new Quantity(promotionalDto.quantity());
        }
        return new Quantity(0);
    }

    private Quantity createNormalQuantity(StockDto normalDto) {
        if (normalDto != null) {
            return new Quantity(normalDto.quantity());
        }
        return new Quantity(0);
    }

    private PromotionType createPromotionType(StockDto promotionalDto) {
        if (promotionalDto != null) {
            return promotionalDto.promotionType();
        }
        return PromotionType.NONE;
    }

    private StockDto findPromotionalDto(List<StockDto> dtos) {
        return dtos.stream()
                .filter(dto -> dto.promotionType().isPromotion())
                .findFirst()
                .orElse(null);
    }

    private StockDto findNormalDto(List<StockDto> dtos) {
        return dtos.stream()
                .filter(dto -> !dto.promotionType().isPromotion())
                .findFirst()
                .orElse(null);
    }
}
