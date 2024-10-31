package nasuxjava.webnexus.controller.admin;

import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import nasuxjava.webnexus.entity.User;

@RequestMapping("/Admin")
@Controller
public class DashboardController {
    private static final String LAYOUT = "admin/template";

    @GetMapping("/Dashboard")
    public String viewDashboard(HttpServletRequest request, Model model) {

        // User user = (User) request.getSession().getAttribute("user");
        // if (user != null) {
        // // Nếu người dùng đã đăng nhập, thêm thông tin vào model
        // model.addAttribute("user", user);
        // model.addAttribute("content", "admin/user/profile.html");
        // return LAYOUT; // Trả về trang profile
        // } else {
        // model.addAttribute("content", "admin/dashboard.html");
        // return LAYOUT;
        // }
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Lấy tên người dùng
            model.addAttribute("username", username); // Thêm tên người dùng vào model
            String roles = authentication.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.joining(", "));
            model.addAttribute("roles", roles); // Thêm tên người dùng vào model
        }
        model.addAttribute("content", "admin/dashboard.html");
        return LAYOUT;

    }
}
