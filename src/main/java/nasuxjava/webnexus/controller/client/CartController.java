package nasuxjava.webnexus.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import nasuxjava.webnexus.entity.Product;
import nasuxjava.webnexus.model.Cart;
import nasuxjava.webnexus.service.CartService;
import nasuxjava.webnexus.services.ProductService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Cart")
public class CartController {
    private static final String LAYOUT = "client/templateOther";

    private final ProductService productService;
    private final CartService cartService;

    private final HttpSession session;

    @Autowired
    public CartController(ProductService productService, CartService cartService, HttpSession session) {
        this.cartService = cartService;
        this.productService = productService;
        this.session = session;

    }

    @GetMapping
    public String viewCart(Model model) {
        Cart cart = cartService.getCart();
        List<Product> products = productService.getAll();
        List<Product> subList = products.subList(0, 3);
        // CartDto cartDto = new CartDto();
        model.addAttribute("products", subList);
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", cart.getToTalCart());
        model.addAttribute("title", "Cart");
        model.addAttribute("content", "/client/cart.html");
        return LAYOUT;
    }

    @PostMapping("/Add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, HttpServletRequest request) {
        Product product = productService.getProductById(productId).orElse(null);
        cartService.addToCart(product, quantity);
        session.setAttribute("message.success", "Product added to cart successfully");
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/Add/{id}")
    public String addGetToCart(@PathVariable Long id, HttpServletRequest request) { // Assume getProductById is a
        int quantity = 1; // method that retrieves a
        // product by its ID
        Product product = productService.getProductById(id).orElse(null);
        cartService.addToCart(product, quantity);
        session.setAttribute("message.success", "Product added to cart successfully");
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/UpdateCart")
    public String updateCart(@ModelAttribute Cart cart) {
        try {
            cartService.updateCart(cart);
            session.setAttribute("message.success", "Cart updated successfully");
        } catch (Exception e) {
            session.setAttribute("message.error", e.getMessage());
        }
        return "redirect:/Cart";
    }

    @GetMapping("/Remove/{id}")
    public String removeGetCart(@PathVariable Long id, HttpServletRequest request) {
        Product product = productService.getProductById(id).orElse(null);
        cartService.removeFromCart(product);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    // @PostMapping("/Remove")
    // public String removeFromCart(@RequestParam Long productId) {
    // Product product = productService.getProductById(productId).orElse(null);
    // cartService.removeFromCart(product);
    // return "redirect:/Cart";
    // }
}
