package nasuxjava.webnexus.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import nasuxjava.webnexus.dto.ProductDto;
import nasuxjava.webnexus.entity.Order;
import nasuxjava.webnexus.entity.Product;
import nasuxjava.webnexus.entity.Order.OrderStatus;
import nasuxjava.webnexus.services.OrderService;
import nasuxjava.webnexus.services.ProductService;

@RequestMapping("/Admin/Orders")
@Controller
public class OrderController {
    private final ProductService productService;
    private final OrderService orderService;
    private final HttpSession session;

    private static final String TEMPLATE = "admin/template";
    private static final String PATH = "/admin/order";
    private static final String REDIRECTLIST = "redirect:/Admin/Orders";
    private static final String REDIRECTADD = "redirect:/Admin/Orders/Add";
    private static final String REDIRECTEDIT = "redirect:/Admin/Orders/Edit";

    @Autowired
    public OrderController(ProductService productService, OrderService orderService, HttpSession session) {
        this.productService = productService;
        this.orderService = orderService;
        this.session = session;
    }

    @GetMapping
    public String viewList(Model model) {
        model.addAttribute("urlDelete", "Orders");
        model.addAttribute("title", "List Order");
        List<Order> orders = orderService.getAllOrder();
        model.addAttribute("orders", orders);
        model.addAttribute("content", PATH + "/list.html");
        return TEMPLATE;
    }

    @GetMapping("/Detail/{id}")
    public String showDetailOrder(@PathVariable Long id, Model model) {
        model.addAttribute("title", "List Detail");
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("content", PATH + "/detail.html");
        return TEMPLATE; // Trả về view hiển thị form sửa người dùng
    }

    // @GetMapping("/Delete/{id}")
    // public String deleteOrrder(@PathVariable Long id, Model model) {
    // model.addAttribute("title", "List Detail");
    // Order order = orderService.getOrderById(id);
    // orderService.deleteOrder(order);
    // model.addAttribute("content", PATH + "/detail.html");
    // return TEMPLATE; // Trả về view hiển thị form sửa người dùng
    // }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Boolean> deleteOrrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        try {
            orderService.deleteOrder(order);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/StatusToProcessing/{id}")
    public String statusToProcessing(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        order.setStatus(OrderStatus.PROCESSING);
        try {
            orderService.saveOrder(order);
            session.setAttribute("message.success", "Change state to Processing successfully");
        } catch (Exception e) {
            session.setAttribute("message.error", e.getMessage());
        }

        return REDIRECTLIST; // Trả về view hiển thị form sửa người dùng
    }

    @GetMapping("/StatusToShipped/{id}")
    public String statusToShipped(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        order.setStatus(OrderStatus.SHIPPED);
        try {
            orderService.saveOrder(order);
            session.setAttribute("message.success", "Change state to Shipped successfully");
        } catch (Exception e) {
            session.setAttribute("message.error", e.getMessage());
        }

        return REDIRECTLIST; // Trả về view hiển thị form sửa người dùng
    }

    @GetMapping("/StatusToDelivered/{id}")
    public String StatusToDelivered(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        order.setStatus(OrderStatus.DELIVERED);
        try {
            orderService.saveOrder(order);
            session.setAttribute("message.success", "Change state to Delivered successfully");
        } catch (Exception e) {
            session.setAttribute("message.error", e.getMessage());
        }

        return REDIRECTLIST; // Trả về view hiển thị form sửa người dùng
    }

}
