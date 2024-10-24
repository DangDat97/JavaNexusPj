package nasuxjava.webnexus.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;

@Controller
public class HomeController {
    private static final String LAYOUT = "client/templateOther";

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("content", "/client/home.html");
        return "client/template";
    }

    @GetMapping("/Shop")
    public String viewShop(Model model) {
        model.addAttribute("content", "/client/shop.html");
        return LAYOUT;
    }

    @GetMapping("/About")
    public String viewAbout(Model model) {
        model.addAttribute("content", "/client/about.html");
        return LAYOUT;
    }

    @GetMapping("/Booking")
    public String viewBooking(Model model) {
        model.addAttribute("content", "/client/bookingForm.html");
        return LAYOUT;
    }

    @GetMapping("/Cart")
    public String viewCart(Model model) {
        model.addAttribute("content", "/client/cart.html");
        return LAYOUT;
    }

    @GetMapping("/Checkout")
    public String viewCheckOut(Model model) {
        model.addAttribute("content", "/client/checkout.html");
        return LAYOUT;
    }

    @GetMapping("/Product")
    public String viewProduct(Model model) {
        model.addAttribute("content", "/client/product.html");
        return LAYOUT;
    }

}
