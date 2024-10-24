package nasuxjava.webnexus.controller.auth;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.*;
import nasuxjava.webnexus.dto.UserDto;
import nasuxjava.webnexus.entity.User;
import nasuxjava.webnexus.services.UserService;

@Controller
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    // handler method to handle home page request
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "/auth/register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        // User existingUser = userService.findUserByEmail(userDto.getEmail());

        // if (existingUser != null && existingUser.getEmail() != null &&
        // !existingUser.getEmail().isEmpty()) {
        // result.rejectValue("email", null,
        // "There is already an account registered with the same email");
        // }

        // if (result.hasErrors()) {
        // model.addAttribute("user", userDto);
        // return "/register";
        // }

        // userService.saveUser(userDto);
        // return "redirect:/register?success";

        // Kiểm tra xem email đã tồn tại chưa

        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if (existingUser != null) {
            result.rejectValue("email", "error.user", "Email đã được sử dụng");
        }

        // Kiểm tra các lỗi validation
        if (result.hasErrors()) {
            return "auth/register";
        }

        // Lưu người dùng mới
        try {
            userService.saveUser(userDto);
            return "redirect:/register?success";
        } catch (Exception e) {
            // Xử lý lỗi khi lưu người dùng
            model.addAttribute("errorMessage", "Đã xảy ra lỗi khi đăng ký. Vui lòng thử lại.");
            return "auth/register";
        }
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {
        model.addAttribute("error", "Đã xảy ra lỗi khi đăng nhập. Vui lòng thử lại.");
        return "auth/login";

        // try {
        // Authentication authentication = authenticationManager.authenticate(
        // new UsernamePasswordAuthenticationToken(email, password));

        // SecurityContextHolder.getContext().setAuthentication(authentication);
        // return "redirect:/index";
        // } catch (Exception e) {
        // model.addAttribute("error", "Invalid Email or password");
        // return "auth/login";
        // }

        // try {
        // if (userService.authenticateUser(email, password)) {
        // // Authentication successful
        // return "redirect:/Admin/Dashboard";
        // } else {
        // // Authentication failed
        // model.addAttribute("error", "Email hoặc mật khẩu không hợp lệ");
        // return "auth/login";
        // }
        // } catch (Exception e) {
        // model.addAttribute("error", "Đã xảy ra lỗi khi đăng nhập. Vui lòng thử
        // lại.");
        // return "auth/login";
        // }
    }
}
