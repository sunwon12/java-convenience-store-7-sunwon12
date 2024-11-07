package store.model.product;

public class Product {

    private String name;
    private int price;
    private int count;
    private PromotionType promotionType;

    public Product(String name, int price, int count, PromotionType promotionType) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.promotionType = promotionType;
    }


    public Product deepCopy() {
        return new Product(
                this.name,
                this.price,
                this.count,
                this.promotionType
        );
    }

    public void updateCount(int count) {
        this.count = count;
    }

    public void minusCount(int count) {
        this.count -= count;
    }

    public boolean isSameProduct(String productName) {
        return name.equals(productName);
    }

    public boolean isPromotion() {
        return this.promotionType != PromotionType.NONE;
    }

    public boolean hasProduct() {
        return this.count > 0;
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

    public boolean isSameNameAndPromotionType(Product other) {
        return this.name.equals(other.getName())
                && this.promotionType.equals(other.getPromotionType());
    }

    public int countMissingPromotionCount() {
        return promotionType.calculateMissingPromotionCount(count);
    }

    public void plus(Product other) {
        this.count += other.count;
    }


}

