package store.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CustomFileReader {
    private static final String PRODUCTS_FILE = "src/main/resources/products.md";
    private static final String PROMOTIONS_FILE = "src/main/resources/promotions.md";
    private static final String HEADER = "name,price,quantity,promotionType";
    private static final String PROMOTION_HEADER = "name,buy,get,start_date,end_date";

    public List<ProductDto> loadProducts() {
        try {
            Path path = Paths.get(PRODUCTS_FILE);
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("프로모션 목록을 불러오는데 실패했습니다.");
            }
            List<String> lines = Files.readAllLines(path);

            return parseProducts(lines);
        } catch (IOException e) {
            throw new IllegalArgumentException("상품 목록을 불러오는데 실패했습니다.", e);
        }
    }

    private List<ProductDto> parseProducts(List<String> lines) {
        if (!lines.get(0).equals(HEADER)) {
            throw new IllegalArgumentException("상품 목록 파일의 형식이 올바르지 않습니다.");
        }

        List<ProductDto> products = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            products.add(parseProductLine(lines.get(i)));
        }
        return products;
    }

    private ProductDto parseProductLine(String line) {
        String[] parts = line.split(",");
        return new ProductDto(
                parts[0],
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                parts[3].equals("null") ? null : parts[3]
        );
    }

    public List<PromotionDto> loadPromotions() {
        try {
            Path path = Paths.get(PROMOTIONS_FILE);
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("프로모션 목록을 불러오는데 실패했습니다.");
            }
            List<String> lines = Files.readAllLines(path);
            return parsePromotions(lines);
        } catch (IOException e) {
            throw new IllegalArgumentException("프로모션 목록을 불러오는데 실패했습니다.", e);
        }
    }

    private List<PromotionDto> parsePromotions(List<String> lines) {
        if (!lines.get(0).equals(PROMOTION_HEADER)) {
            throw new IllegalArgumentException("프로모션 목록 파일의 형식이 올바르지 않습니다.");
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
