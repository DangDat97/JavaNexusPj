package nasuxjava.webnexus.services;

import nasuxjava.webnexus.entity.Product;
import nasuxjava.webnexus.model.Cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);

    Optional<Product> getProductById(Long id);

    Product saveProduct(Product product);

    void deleteProduct(Long id);

    long getProductCount();

    Page<Product> getProductsByCategory(Long categoryId, Pageable pageable);

    List<Product> searchProductsByName(String name);

    List<Product> getProductsByPriceRange(double minPrice, double maxPrice);

    public void addProductToCart(Cart cart, Long productId, int quantity);
    // List<Product> getTopRatedProducts(int limit);

    List<Product> getProductsByCategoryAndPriceRange(Long categoryId, double minPrice, double maxPrice);
}