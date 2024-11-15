package store.model.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import store.model.dto.PromotionDto;
import store.model.dto.StockDto;
import store.model.dto.StockDtos;
import store.model.product.PromotionType;

public class CustomFileReader {

    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";
    private static final String PRODUCT_FILE_HEADER = "name,price,quantity,promotion";
    private static final String PROMOTION_FILE_HEADER = "name,buy,get,start_date,end_date";

    public StockDtos loadProducts() {
        try {
            Path path = Paths.get(PRODUCTS_FILE_PATH);
            validateProductFilePath(path);
            List<String> lines = Files.readAllLines(path);
            return StockDtos.from(parseProducts(lines));
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.FAILED_LOAD_PRODUCT_FILE.getMessage());
        }
    }

    private void validateProductFilePath(Path path) {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_FOUND_FILE_PATH.getMessage());
        }
    }

    private List<StockDto> parseProducts(List<String> lines) {
        if (!lines.getFirst().equals(PRODUCT_FILE_HEADER)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_PRODUCT_FORMAT.getMessage());
        }

        List<StockDto> products = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            products.add(parseProductLine(lines.get(i)));
        }
        return products;
    }

    private StockDto parseProductLine(String line) {
        String[] parts = line.split(",");
        return new StockDto(
                parts[0],
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                PromotionType.from(parts[3])
        );
    }

    public List<PromotionDto> loadPromotions() {
        try {
            Path path = Paths.get(PROMOTIONS_FILE_PATH);
            validatePromotionFilePath(path);
            List<String> lines = Files.readAllLines(path);
            return parsePromotions(lines);
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.FAILED_LOAD_PROMOTION_FILE.getMessage());
        }
    }

    private static void validatePromotionFilePath(Path path) {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_FOUND_FILE_PATH.getMessage());
        }
    }

    private List<PromotionDto> parsePromotions(List<String> lines) {
        if (!lines.getFirst().equals(PROMOTION_FILE_HEADER)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_PRODUCT_FORMAT.getMessage());
        }

        List<PromotionDto> promotions = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            promotions.add(parsePromotionLine(lines.get(i)));
        }
        return promotions;
    }

    private PromotionDto parsePromotionLine(String line) {
        String[] parts = line.split(",");
        return new PromotionDto(
                parts[0],
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                parts[3],
                parts[4]
        );
    }
}
