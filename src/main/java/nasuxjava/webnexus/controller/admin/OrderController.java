package nasuxjava.webnexus.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import nasuxjava.webnexus.dto.ProductDto;
import nasuxjava.webnexus.entity.Order;
import nasuxjava.webnexus.entity.Product;
import nasuxjava.webnexus.services.OrderService;
import nasuxjava.webnexus.services.ProductService;

@RequestMapping("/Admin/Orders")
@Controller
public class OrderController {
    private final ProductService productService;
    private final OrderService orderService;

    private static final String TEMPLATE = "admin/template";
    private static final String PATH = "/admin/order";
    private static final String REDIRECTLIST = "redirect:/Admin/Orders";
    private static final String REDIRECTADD = "redirect:/Admin/Orders/Add";
    private static final String REDIRECTEDIT = "redirect:/Admin/Orders/Edit";

    @Autowired
    public OrderController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping
    public String viewList(Model model) {
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
}
