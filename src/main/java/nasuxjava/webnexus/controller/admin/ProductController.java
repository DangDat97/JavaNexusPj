package nasuxjava.webnexus.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import nasuxjava.webnexus.dto.ProductDto;
import nasuxjava.webnexus.dto.ProductFilterDto;
import nasuxjava.webnexus.entity.Category;
import nasuxjava.webnexus.entity.Product;
import nasuxjava.webnexus.services.CategoryService;
import nasuxjava.webnexus.services.DistributorService;
import nasuxjava.webnexus.services.ProductService;

@RequestMapping("/Admin/Products")
@Controller
public class ProductController {

    private static final String TEMPLATE = "admin/template";
    private static final String PATH = "/admin/product";
    private static final String REDIRECTLIST = "redirect:/Admin/Products";
    private static final String REDIRECTADD = "redirect:/Admin/Products/Add";
    private static final String REDIRECTEDIT = "redirect:/Admin/Products/Edit";

    @Autowired
    private ProductService productService;
    @Autowired
    private DistributorService distributorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private HttpSession session;

    @GetMapping
    public String listProducts(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size, Model model) {
        Page<Product> productPage = productService.getAllProducts(PageRequest.of(page, size));
        List<Product> products = productPage.getContent();
        model.addAttribute("distributors", distributorService.getAllDistributors());
        model.addAttribute("countproduct", productService.countProduct());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("productFilterDto", new ProductFilterDto());
        model.addAttribute("products", products);
        model.addAttribute("urlDelete", "Products");
        model.addAttribute("title", "List Products");
        model.addAttribute("content", PATH + "/list.html");
        return TEMPLATE;
    }

    @GetMapping("/Filter")
    public String filterproduct(@ModelAttribute("productFilterDto") ProductFilterDto productFilterDto,
            Model model) {
        List<Product> products = new ArrayList<>();
        model.addAttribute("title", "List Products");
        model.addAttribute("urlDelete", "Products");
        model.addAttribute("content", PATH + "/list.html");
        model.addAttribute("productFilterDto", productFilterDto);
        model.addAttribute("distributors", distributorService.getAllDistributors());
        model.addAttribute("countproduct", productService.countProduct());
        try {
            Page<Product> productPage = productService.filterProducts(productFilterDto);
            products = productPage.getContent();
            model.addAttribute("totalPages", productPage.getTotalPages());
        } catch (Exception e) {
            session.setAttribute("message.error", e.getMessage());
            return TEMPLATE;
        }
        model.addAttribute("products", products);
        model.addAttribute("size", productFilterDto.getSize());
        model.addAttribute("currentPage", productFilterDto.getPage());
        return TEMPLATE;
    }

    @GetMapping("/Add")
    public String showAddproductForm(Model model) {

        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("distributors", distributorService.getAllDistributors());
        model.addAttribute("title", "Add Products");
        model.addAttribute("content", PATH + "/add.html");
        return TEMPLATE;
    }

    @PostMapping("/Add")
    public String addproduct(@Valid @ModelAttribute("productDto") ProductDto productDto,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {

            return "auth/register";
        }

        try {
            productService.saveProduct(productDto);
        } catch (Exception e) {
            session.setAttribute("message.error", e.getMessage());
            return REDIRECTADD;
        }
        session.setAttribute("message.success", "New product added successfully");
        return REDIRECTADD;
    }

    @GetMapping("/Edit/{id}")
    public String showEditproductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        ProductDto productDto = productService.convertProductToDto(product);
        List<Category> selectedCategories = categoryService.getCategoriesById(productDto.getCategories());
        model.addAttribute("selectedCategories", selectedCategories);
        model.addAttribute("distributors", distributorService.getAllDistributors());
        model.addAttribute("title", "Edit Products");
        model.addAttribute("productDto", productDto);
        model.addAttribute("content", PATH + "/edit.html");
        return TEMPLATE; // Trả về view hiển thị form sửa người dùng
    }

    @GetMapping("/Detail/{id}")
    public String showDetailproductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        ProductDto productDto = productService.convertProductToDto(product);
        model.addAttribute("title", "Details product");
        model.addAttribute("productDto", productDto);
        model.addAttribute("content", PATH + "/detail.html");
        return TEMPLATE; // Trả về view hiển thị form sửa người dùng
    }

    @PostMapping("/Edit/{id}")
    public String updateproduct(@Valid @ModelAttribute("productDto") ProductDto productDto,
            @PathVariable Long id,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            session.setAttribute("message.error", result.getAllErrors());
            return REDIRECTEDIT + "/" + id;
        }
        // product product = productService.getproductById(id).orElseThrow();
        // if (product.getPassword().equals(productDto.getPassword()) != true &&
        // productDto.getPassword() != null) {
        // productDto.setPassword(passwordEncoder.encode(productDto.getPassword()));
        // }
        // product = productService.convertDtoToEntity(productDto, product);
        productDto.setId(id);
        try {
            productService.updateProduct(productDto);
            session.setAttribute("message.success", "Update product Success");
        } catch (Exception e) {
            session.setAttribute("message.error", e.getMessage());
            return REDIRECTEDIT + "/" + id;
        }
        session.setAttribute("message.success", "Update product Success");
        return REDIRECTEDIT + "/" + id; // Chuyển hướng về danh sách người dùng sau khi sửa
    }

    @DeleteMapping("/Delete/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteproduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            session.setAttribute("message.error", e.getMessage());
            return ResponseEntity.ok(false);
        }

    }
}