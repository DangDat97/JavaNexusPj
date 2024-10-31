package nasuxjava.webnexus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nasuxjava.webnexus.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Custom method to find products by category ID with pagination
    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId")
    Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    // Find products by name containing the given string (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Find products by price range
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    // Find top N products by rating
    // @Query("SELECT p FROM Product p ORDER BY p.rating DESC")
    // List<Product> findTopNProductsByRating(Pageable pageable);

    // Find products by category ID and price range
    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId AND p.price BETWEEN:minPrice AND:maxPrice")
    List<Product> findByCategoryIdAndPriceRange(@Param("categoryId") Long categoryId,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice);

    // The following methods are provided by JpaRepository:
    // findAll(Pageable pageable)
    // findById(Long id)
    // save(Product product)
    // deleteById(Long id)
    // count()
}