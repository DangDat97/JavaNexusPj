package nasuxjava.webnexus.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import nasuxjava.webnexus.entity.Order;
import nasuxjava.webnexus.entity.Product;
import nasuxjava.webnexus.entity.User;
import nasuxjava.webnexus.model.Cart;
import nasuxjava.webnexus.service.CartService;
import nasuxjava.webnexus.service.RandomService;
import nasuxjava.webnexus.services.OrderService;
import nasuxjava.webnexus.services.ProductService;
import nasuxjava.webnexus.services.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

@Controller
public class HomeController {
    private static final String LAYOUT = "client/templateOther";

    private final ProductService productService;
    private final CartService cartService;
    private final RandomService randomService;
    private final UserService userService;
    private final OrderService orderService;
    private final HttpSession session;

    @Autowired
    public HomeController(ProductService productService, CartService cartService, RandomService randomService,
            UserService userService, OrderService orderService, HttpSession session) {
        this.productService = productService;
        this.cartService = cartService;
        this.randomService = randomService;
        this.userService = userService;
        this.orderService = orderService;
        this.session = session;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("content", "/client/home.html");
        return "client/template";
    }

    @GetMapping("/Shop")
    public String viewShop(Model model, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getAllProducts(pageable);
        model.addAttribute("totalPrice", cart.getToTalCart());
        model.addAttribute("title", "Shop");
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("content", "/client/shop.html");

        return LAYOUT;
    }

    @GetMapping("/Shop/Category/{id}")
    public String viewShopCategory(Model model,
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getProductsByCategory(id, pageable);

        model.addAttribute("title", "Shop");
        model.addAttribute("totalPrice", cart.getToTalCart());
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("content", "/client/shop.html");

        return LAYOUT;
    }

    @GetMapping("/Product/{id}")
    public String viewProduct(@PathVariable Long id,
            Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        Cart cart = cartService.getCart();
        model.addAttribute("totalPrice", cart.getToTalCart());
        model.addAttribute("cart", cart);
        model.addAttribute("title", "Product");
        model.addAttribute("product", product);
        model.addAttribute("content", "/client/product.html");
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

    @GetMapping("/Checkout")
    public String viewCheckOut(Model model) {
        Cart cart = cartService.getCart();
        User user = new User();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            user = (User) model.getAttribute("infoUserLogin");
        }
        model.addAttribute("title", "Checkout");
        model.addAttribute("totalPrice", cart.getToTalCart());
        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        model.addAttribute("content", "/client/checkout.html");
        return LAYOUT;
    }

    @PostMapping("/Checkout")
    public String addOrder(@ModelAttribute("user") User user,
            Model model) {
        User userAdd = new User();
        User existingUser = userService.findUserByEmail(user.getEmail());
        Cart cart = cartService.getCart();

        if (cart == null) {
            session.setAttribute("message.error",
                    "Your cart is empty. Add products to cart to checkout.");
            return "redirect:/Checkout";
        }

        if (existingUser != null) {
            userAdd = existingUser;
        } else {
            String password = randomService.getRandomString(10);
            userAdd = userService.saveUserOrder(user, password);
            session.setAttribute("message.success",
                    "Successfully created an account with email. Check your email to receive login information and order information.");
        }

        Order order = orderService.addCarttoOrder(cart, userAdd);
        if (order != null) {
            if (session.getAttribute("message.success") != null) {
                session.setAttribute("message.success",
                        session.getAttribute("message.success") + "Order created successfully");
            }
            session.setAttribute("message.success", "Order created successfully");
        }
        cartService.clear();
        return "redirect:/Checkout";
    }

}
