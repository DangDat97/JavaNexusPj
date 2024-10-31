package nasuxjava.webnexus.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import nasuxjava.webnexus.entity.Product;
import nasuxjava.webnexus.model.Cart;
import nasuxjava.webnexus.service.CartService;
import nasuxjava.webnexus.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

@Controller
public class HomeController {
    private static final String LAYOUT = "client/templateOther";

    private final ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("content", "/client/home.html");
        return "client/template";
    }

    @GetMapping("/Shop")
    public String viewShop(Model model, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getAllProducts(pageable);

        model.addAttribute("title", "Shop");
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("content", "/client/shop.html");

        return LAYOUT;
    }

    @GetMapping("/About")
    public String viewAbout(Model model) {
        model.addAttribute("title", "About");
        model.addAttribute("content", "/client/about.html");
        return LAYOUT;
    }

    @GetMapping("/Booking")
    public String viewBooking(Model model) {

        model.addAttribute("title", "Booking");
        model.addAttribute("content", "/client/bookingForm.html");
        return LAYOUT;
    }

    @GetMapping("/AddToCart/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {
        Cart cart = cartService.getCart(session);
        productService.addProductToCart(cart, id, 1); // Giả sử có phương thức này trong ProductService
        cartService.saveCart(session, cart); // Lưu giỏ hàng sau khi thêm sản phẩm
        return "redirect:/Cart"; // Chuyển hướng đến trang giỏ hàng
    }

    @GetMapping("/Cart")
    public String viewCart(HttpSession session, Model model) {
        Cart cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        model.addAttribute("title", "Cart");
        model.addAttribute("products", cart.getProducts()); // Thêm dòng này để lấy danh sách sản phẩm trong giỏ hàng
        model.addAttribute("content", "/client/cart.html");
        return LAYOUT;
    }

    @GetMapping("/Checkout")
    public String viewCheckOut(Model model) {
        model.addAttribute("title", "Checkout");
        model.addAttribute("content", "/client/checkout.html");
        return LAYOUT;
    }

    @GetMapping("/Product")
    public String viewProduct(Model model) {
        model.addAttribute("title", "Checkout");
        model.addAttribute("content", "/client/product.html");
        return LAYOUT;
    }

}
