package nasuxjava.webnexus.repository;

import nasuxjava.webnexus.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    // Tìm tất cả ảnh của một sản phẩm cụ thể
    List<ProductImage> findByProductId(Long productId);

    // Đếm số lượng ảnh của một sản phẩm
    long countByProductId(Long productId);

    // Xóa tất cả ảnh của một sản phẩm
    void deleteByProductId(Long productId);

    // Tìm ảnh theo đường dẫn
    ProductImage findByImagePath(String imagePath);

    // Kiểm tra xem một đường dẫn ảnh đã tồn tại chưa
    boolean existsByImagePath(String imagePath);

    // Tìm tất cả ảnh của nhiều sản phẩm
    List<ProductImage> findByProductIdIn(List<Long> productIds);
}