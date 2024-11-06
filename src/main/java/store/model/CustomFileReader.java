package store.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import store.model.product.PromotionType;

public class CustomFileReader {

    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";
    private static final String PRODUCT_FILE_HEADER = "name,price,quantity,promotion";
    private static final String PROMOTION_FILE_HEADER = "name,buy,get,start_date,end_date";

    public List<StockProductDto> loadProducts() {
        try {
            Path path = Paths.get(PRODUCTS_FILE_PATH);
            if (!Files.exists(path)) {
                throw new IllegalArgumentException(ErrorMessage.NOT_FOUND_FILE_PATH.getMessage());
            }
            List<String> lines = Files.readAllLines(path);

            return parseProducts(lines);
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.FAILED_LOAD_PRODUCT_FILE.getMessage());
        }
    }

    private List<StockProductDto> parseProducts(List<String> lines) {
        if (!lines.get(0).equals(PRODUCT_FILE_HEADER)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_PRODUCT_FORMAT.getMessage());
        }

        List<StockProductDto> products = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            products.add(parseProductLine(lines.get(i)));
        }
        return products;
    }

    private StockProductDto parseProductLine(String line) {
        String[] parts = line.split(",");
        return  StockProductDto.of(
                parts[0],
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                PromotionType.from(parts[3])
        );
    }

    public List<PromotionDto> loadPromotions() {
        try {
            Path path = Paths.get(PROMOTIONS_FILE_PATH);
            if (!Files.exists(path)) {
                throw new IllegalArgumentException(ErrorMessage.NOT_FOUND_FILE_PATH.getMessage());
            }
            List<String> lines = Files.readAllLines(path);
            return parsePromotions(lines);
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.FAILED_LOAD_PROMOTION_FILE.getMessage());
        }
    }

    private List<PromotionDto> parsePromotions(List<String> lines) {
        if (!lines.get(0).equals(PROMOTION_FILE_HEADER)) {
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
