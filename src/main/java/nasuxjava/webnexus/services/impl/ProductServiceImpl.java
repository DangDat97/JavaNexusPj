package nasuxjava.webnexus.services.impl;

import nasuxjava.webnexus.entity.Product;
import nasuxjava.webnexus.model.Cart;
import nasuxjava.webnexus.repository.ProductRepository;
import nasuxjava.webnexus.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public long getProductCount() {
        return productRepository.count();
    }

    @Override
    public Page<Product> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    // @Override
    // public List<Product> getTopRatedProducts(int limit) {
    // return productRepository.findTopNProductsByRating(Pageable.ofSize(limit));
    // }

    @Override
    public void addProductToCart(Cart cart, Long productId, int quantity) {
        if (quantity <= 0) {
            quantity = 1; // Thiết lập giá trị mặc định là 1 nếu quantity không hợp lệ
        }
        Optional<Product> productOpt = getProductById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            cart.addItem(productId, quantity, product.getPrice()); // Giả sử thêm 1 sản phẩm
        }
    }

    @Override
    public List<Product> getProductsByCategoryAndPriceRange(Long categoryId, double minPrice, double maxPrice) {
        return productRepository.findByCategoryIdAndPriceRange(categoryId, minPrice, maxPrice);
    }
}