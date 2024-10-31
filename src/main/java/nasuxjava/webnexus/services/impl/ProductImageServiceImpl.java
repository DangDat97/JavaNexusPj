package nasuxjava.webnexus.services.impl;

import nasuxjava.webnexus.entity.ProductImage;
import nasuxjava.webnexus.entity.Product;
import nasuxjava.webnexus.repository.ProductImageRepository;
import nasuxjava.webnexus.services.ProductImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    @Autowired
    public ProductImageServiceImpl(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    public ProductImage saveProductImage(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    @Override
    public Optional<ProductImage> getProductImageById(Long id) {
        return productImageRepository.findById(id);
    }

    @Override
    public List<ProductImage> getProductImagesByProduct(Product product) {
        return productImageRepository.findByProductId(product.getId());
    }

    @Override
    public void deleteProductImage(Long id) {
        productImageRepository.deleteById(id);
    }

    @Override
    public ProductImage updateProductImage(Long id, String newImagePath) {
        Optional<ProductImage> optionalProductImage = productImageRepository.findById(id);
        if (optionalProductImage.isPresent()) {
            ProductImage productImage = optionalProductImage.get();
            productImage.setImagePath(newImagePath);
            return productImageRepository.save(productImage);
        }
        return null;
    }
}