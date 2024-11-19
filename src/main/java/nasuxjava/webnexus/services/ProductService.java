package nasuxjava.webnexus.services;

import nasuxjava.webnexus.dto.ProductDto;
import nasuxjava.webnexus.dto.ProductFilterDto;
import nasuxjava.webnexus.entity.Product;
import nasuxjava.webnexus.model.Cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<Product> getAllProducts(Pageable pageable);

    Optional<Product> getProductById(Long id);

    void saveProduct(ProductDto productDto);

    void deleteProduct(Long id);

    long getProductCount();

    List<Product> getAll();

    Page<Product> getProductsByCategory(Long categoryId, Pageable pageable);

    List<Product> searchProductsByName(String name);

    List<Product> getProductsByPriceRange(double minPrice, double maxPrice);

    // public void addProductToCart(Cart cart, Long productId, int quantity);

    // List<Product> getTopRatedProducts(int limit);
    // ProductDto convertEntityToDto(Product product);
    ProductDto convertProductToDto(Product product);

    // List<ProductDto> convertListProductEntityToDto(List<Product> products);
    // Page<Product> getProductsByFillterQ(ProductFilterDto productFilterDto);
    Page<Product> filterProducts(ProductFilterDto productFilterDto);

    Page<Product> getProductsByFillter(ProductFilterDto productFilterDto);

    Product updateProduct(ProductDto productDto);

    long countProduct();
}