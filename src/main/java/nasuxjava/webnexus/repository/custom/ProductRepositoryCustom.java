package nasuxjava.webnexus.repository.custom;

import java.math.BigDecimal;
import java.util.List;

import nasuxjava.webnexus.entity.Product;

public interface ProductRepositoryCustom {
    List<Product> findProductsByCriteria(String name, BigDecimal minPrice, BigDecimal maxPrice,
            List<Long> categories, Long distributor, Boolean status);
}
