package store.model.product;

import java.util.Objects;

public record ProductName(String name) {

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
