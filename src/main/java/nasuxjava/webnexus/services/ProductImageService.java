package nasuxjava.webnexus.services;

import nasuxjava.webnexus.entity.ProductImage;
import nasuxjava.webnexus.entity.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductImageService {

    @Transactional
    ProductImage saveProductImage(ProductImage productImage);

    Optional<ProductImage> getProductImageById(Long id);

    List<ProductImage> getProductImagesByProduct(Product product);

    @Transactional
    void deleteProductImage(Long id);

    @Transactional
    ProductImage updateProductImage(Long id, String newImagePath);
}