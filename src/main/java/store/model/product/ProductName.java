package store.model.product;

import java.util.Objects;

public class ProductName {

    private final String name;

    public ProductName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        ProductName productName = (ProductName) o;
        return name.equals(productName.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
