package nasuxjava.webnexus.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/Admin")
@Controller
public class DashboardController {
    private static final String LAYOUT = "admin/template";

    @GetMapping("/Dashboard")
    public String viewDashboard(Model model) {
        model.addAttribute("content", "/admin/dashboard.html");
        return LAYOUT;
    }
}
