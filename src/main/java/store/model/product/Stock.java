package store.model.product;

import java.util.ArrayList;
import java.util.List;
import store.model.CustomFileReader;
import store.model.dto.StockProductDto;

public class Stock {

    private List<Product> products;

    public Stock() {
        this.products = new ArrayList<>();
    }

    /**
     * 파일에서 물건 목록을 가져와 창고에 넣어준다
     */
    public void initializeStock() {
        List<StockProductDto> stockProductDtos = new CustomFileReader().loadProducts();
    }
}
