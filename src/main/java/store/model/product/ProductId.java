package store.model.product;

public class ProductId {

    private final String name;
    private final int price;

    public ProductId(String name, int price) {
        this.name = name;
        this.price = price;
    }
//
//    protected String getName() {
//        return name;
//    }
//
//    protected int getPrice() {
//        return price;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductId other = (ProductId) o;
        return name.equals(other.name) && price == other.price;
    }

}
