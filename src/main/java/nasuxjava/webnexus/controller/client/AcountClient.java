package nasuxjava.webnexus.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import nasuxjava.webnexus.entity.Order;
import nasuxjava.webnexus.entity.User;
import nasuxjava.webnexus.entity.Order.OrderStatus;
import nasuxjava.webnexus.services.OrderService;
import nasuxjava.webnexus.services.UserService;

import org.springframework.ui.Model;

@RequestMapping("/Account")
@Controller
public class AcountClient {

    private final UserService userService;
    private final OrderService orderService;

    private final HttpSession session;

    private final PasswordEncoder passwordEncoder;

    private static final String LAYOUT = "client/templateOther";
    private static final String PATH_ACOUNT = "/client/acount";

    @Autowired
    public AcountClient(UserService userService, HttpSession session, PasswordEncoder passwordEncoder,
            OrderService orderService) {
        this.userService = userService;
        this.session = session;
        this.passwordEncoder = passwordEncoder;
        this.orderService = orderService;
    }

    @GetMapping("/Dashboard")
    public String viewDashboard(Model model) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("content", PATH_ACOUNT + "/dashboard.html");
        return LAYOUT;
    }

    @GetMapping("/Address")
    public String viewAddress(Model model) {
        model.addAttribute("title", "Address");
        model.addAttribute("content", PATH_ACOUNT + "/address.html");
        return LAYOUT;
    }

    @GetMapping("/AddressEdit")
    public String viewAddressEdit(Model model) {
        model.addAttribute("title", "AddressEdit");
        model.addAttribute("content", PATH_ACOUNT + "/addressEdit.html");
        return LAYOUT;
    }

    @GetMapping("/Details")
    public String viewDetails(Model model) {
        User user = new User();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            user = (User) model.getAttribute("infoUserLogin");
        }
        model.addAttribute("user", user);
        model.addAttribute("title", "Details");
        model.addAttribute("content", PATH_ACOUNT + "/details.html");
        return LAYOUT;
    }

    @PostMapping("/Details")
    public String updateDetails(@ModelAttribute("user") User user,
            @RequestParam String password_current,
            @RequestParam String password_1,
            @RequestParam String password_2,
            Model model) {
        String email = user.getEmail();
        User infoUser = userService.findUserByEmail(email);
        if (infoUser == null) {
            session.setAttribute("message.error", "Your email account is incorrect");
            return "redirect:/Account/Details";
        }

        Boolean check = passwordEncoder.matches(password_current, infoUser.getPassword());
        if (!check) {
            session.setAttribute("message.error", "The old password you entered is incorrect.");
            return "redirect:/Account/Details";
        }

        if (!password_1.equals(password_2)) {
            session.setAttribute("message.error", "New password and re-entered password do not match");
            return "redirect:/Account/Details";
        }
        infoUser.setPassword(passwordEncoder.encode(password_1));
        infoUser.setAddress(user.getAddress());
        infoUser.setFullName(user.getFullName());
        infoUser.setPhone(user.getPhone());
        userService.updateUserDetails(infoUser);
        session.setAttribute("message.success", "Your password has been changed successfully.");

        return "redirect:/Account/Details";

    }

    @GetMapping("/Order/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Order");
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("content", PATH_ACOUNT + "/oder.html");
        return LAYOUT;
    }

    @GetMapping("/OrderAgain/{id}")
    public String handelOrderAgain(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Order");
        Order order = orderService.getOrderById(id);
        orderService.saveOrder(order);
        model.addAttribute("order", order);
        model.addAttribute("content", PATH_ACOUNT + "/oder.html");
        session.setAttribute("message.success", "Reorder successful.");
        return LAYOUT;
    }

    @GetMapping("/CancelOrder/{id}")
    public String handelCancelOrder(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Order");
        Order order = orderService.getOrderById(id);
        order.setStatus(OrderStatus.CANCELLED);
        orderService.saveOrder(order);
        model.addAttribute("order", order);
        model.addAttribute("content", PATH_ACOUNT + "/oder.html");
        session.setAttribute("message.success", "Order cancellation successful.");
        return LAYOUT;
    }

    @GetMapping("/Orders")
    public String viewOrders(Model model) {
        List<Order> listOrder = new ArrayList<>();
        User user = new User();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            user = (User) model.getAttribute("infoUserLogin");

        }
        listOrder = orderService.getOrdersByUser(user);
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("title", "Orders");
        model.addAttribute("content", PATH_ACOUNT + "/orders.html");
        return LAYOUT;
    }

}
