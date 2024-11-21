package nasuxjava.webnexus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nasuxjava.webnexus.entity.Distributor;
import nasuxjava.webnexus.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
                JpaSpecificationExecutor<Product> {

        // Custom method to find products by category ID with pagination
        @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId")
        Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

        // Find products by name containing the given string (case-insensitive)
        List<Product> findByNameContainingIgnoreCase(String name);

        // Find products by price range
        List<Product> findByPriceBetween(double minPrice, double maxPrice);

        List<Product> findByNameContainingAndStatusAndDistributorAndPriceBetween(
                        String name, Boolean status, Distributor distributor, BigDecimal minPrice, BigDecimal maxPrice);

        List<Product> findByCategoriesIn(List<Long> categories);

        List<Product> findByNameContainingAndStatusAndDistributorAndPriceBetweenAndCategoriesIn(
                        String name, Boolean status,
                        Distributor distributor, BigDecimal minPrice, BigDecimal maxPrice,
                        List<Long> categories);

        // @Query("SELECT p FROM Product p WHERE " + "(:name IS NULL OR p.name LIKE
        // %:name%) AND "
        // + "(:status IS NULL OR p.status = :status) AND "
        // + "(:categories IS NULL OR p.categories IN :categories) AND "
        // + "(:distributor IS NULL OR p.distributor = :distributor) AND "
        // + "(:minPrice IS NULL OR p.price >= :minPrice) AND "
        // + "(:maxPrice IS NULL OR p.price <= :maxPrice)")
        // List<Product> filterProducts(@Param("name") String name, @Param("status")
        // Boolean status,
        // @Param("categories") List<Long> categories, @Param("distributor") Distributor
        // distributor,
        // @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal
        // maxPrice);

}