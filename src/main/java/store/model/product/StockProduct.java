package store.model.product;

public class StockProduct {

    private String name;
    private int price;
    private int count;
    private PromotionType promotionType;

    public StockProduct(String name, int price, int count, PromotionType promotionType) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.promotionType = promotionType;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public int getCount() {
        return count;
    }
}

