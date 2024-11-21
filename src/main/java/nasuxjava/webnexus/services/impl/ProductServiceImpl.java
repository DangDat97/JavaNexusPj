package nasuxjava.webnexus.services.impl;

import nasuxjava.webnexus.dto.ProductDto;
import nasuxjava.webnexus.dto.ProductFilterDto;
import nasuxjava.webnexus.entity.Distributor;
import nasuxjava.webnexus.entity.Product;
import nasuxjava.webnexus.entity.ProductImage;
import nasuxjava.webnexus.entity.Category;
import nasuxjava.webnexus.repository.ProductRepository;
import nasuxjava.webnexus.repository.specification.ProductSpecification;
import nasuxjava.webnexus.services.CategoryService;
import nasuxjava.webnexus.services.DistributorService;
import nasuxjava.webnexus.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final String PathUpload = "src/main/resources/static/uploads/";

    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final DistributorService distributorService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService,
            DistributorService distributorService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.distributorService = distributorService;
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    private Product converProductDtoProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDetail(productDto.getDetail());
        product.setStatus(productDto.getStatus());
        product.setDescription(productDto.getDescription());
        if (!productDto.getImage().isEmpty()) {
            product.setImage(productDto.getImage().getOriginalFilename());
            try {
                importImage(productDto.getImage());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        Distributor distributor = distributorService.getDistributorById(Long.valueOf(productDto.getDistributor()));
        product.setDistributor(distributor);
        List<Category> categories = new ArrayList<>();
        List<Long> listIdCategories = productDto.getCategories();
        for (Long id : listIdCategories) {
            categories.add(categoryService.getCategoryById(id));
        }
        product.setCategories(categories);

        return product;
    }

    @Override
    public ProductDto convertProductToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDetail(product.getDetail());
        productDto.setDescription(product.getDescription());
        productDto.setImageName(product.getName());
        productDto.setStatus(product.getStatus());
        productDto.setDistributor(product.getDistributor().getId());
        List<Category> categories = product.getCategories();
        List<Long> categoryIds = categories.stream().map(Category::getId).collect(Collectors.toList());
        productDto.setCategories(categoryIds);
        List<ProductImage> images = product.getImages();
        List<String> imagesName = images.stream().map(ProductImage::getImagePath).collect(Collectors.toList());
        productDto.setLImageName(imagesName);
        return productDto;
    }

    @Override
    public Product updateProduct(ProductDto productDto) {
        Product product = new Product();
        Product productOld = productRepository.getReferenceById(productDto.getId());

        product = converProductDtoProduct(productDto);
        if (product.getImage() == null) {
            product.setImage(productOld.getImage());
        }
        if (product.getImages() == null) {
            product.setImages(productOld.getImages());

        }
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    };

    public void importImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is Empty");
        }
        if (!Files.exists(Paths.get(PathUpload))) {
            Files.createDirectories(Paths.get(PathUpload));
        }
        String fileName = file.getOriginalFilename();
        String filePath = PathUpload + fileName;
        try {
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    public void importImages(List<MultipartFile> files) throws IOException {
        if (!Files.exists(Paths.get(PathUpload))) {
            Files.createDirectories(Paths.get(PathUpload));
        }
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String filePath = PathUpload + fileName;
            try {
                Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                if (e instanceof FileAlreadyExistsException) {
                    throw new RuntimeException("A file of that name already exists.");
                }

                throw new RuntimeException(e.getMessage());
            }
        }

    }

    @Override
    public void saveProduct(ProductDto productDto) {
        Product product = converProductDtoProduct(productDto);
        if (!productDto.getImages().isEmpty()) {
            List<ProductImage> listPImages = new ArrayList();
            List<MultipartFile> listFiles = productDto.getImages();
            for (MultipartFile file : listFiles) {
                ProductImage productImage = new ProductImage();
                productImage.setImagePath(file.getOriginalFilename());
                productImage.setProduct(product);
                listPImages.add(productImage);
            }

            try {
                product.setImages(listPImages);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            // product.setImage(productDto.getImage().getOriginalFilename());
            try {
                importImages(productDto.getImages());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        product.setStatus(true);
        productRepository.save(product);
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

    @Override
    public Page<Product> getProductsByFillter(ProductFilterDto productFilterDto) {
        // Pageable pageable = PageRequest.of(productFilterDto.getPage(),
        // productFilterDto.getSize());
        Distributor distributor = distributorService.getDistributorById(productFilterDto.getDistributor());
        List<Product> listProducts = productRepository
                .findByNameContainingAndStatusAndDistributorAndPriceBetweenAndCategoriesIn(
                        productFilterDto.getName(), productFilterDto.getStatus(), distributor,
                        productFilterDto.getMin_price(), productFilterDto.getMax_price(),
                        productFilterDto.getCategories());

        return convertListToPage(listProducts, productFilterDto.getPage(), productFilterDto.getSize());
    }

    public Page<Product> convertListToPage(List<Product> products, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        int start = Math.min((int) pageable.getOffset(), products.size());
        int end = Math.min((start + pageable.getPageSize()), products.size());
        List<Product> paginatedList = products.subList(start, end);
        return new PageImpl<>(paginatedList, pageable, products.size());
    }

    @Override
    public Page<Product> filterProducts(ProductFilterDto productFilterDto) {
        Specification<Product> specification = Specification
                .where(ProductSpecification.hasName(productFilterDto.getName()))
                .and(ProductSpecification.hasPriceBetween(productFilterDto.getMin_price(),
                        productFilterDto.getMax_price()))
                .and(ProductSpecification.hasStatus(productFilterDto.getStatus()))
                .and(ProductSpecification.hasDistributor(productFilterDto.getDistributor()))
                .and(ProductSpecification.hasCategories(productFilterDto.getCategories()));
        List<Product> listProducts = productRepository.findAll(specification);
        return convertListToPage(listProducts, productFilterDto.getPage(), productFilterDto.getSize());
    }

    // @Override
    // public void addProductToCart(Cart cart, Long productId, int quantity) {
    // if (quantity <= 0) {
    // quantity = 1; // Thiết lập giá trị mặc định là 1 nếu quantity không hợp lệ
    // }
    // Optional<Product> productOpt = getProductById(productId);
    // if (productOpt.isPresent()) {
    // Product product = productOpt.get();
    // cart.addItem(productId, quantity, product.getPrice()); // Giả sử thêm 1 sản
    // phẩm
    // }
    // }

    @Override
    public long countProduct() {
        return productRepository.count();
    };
}