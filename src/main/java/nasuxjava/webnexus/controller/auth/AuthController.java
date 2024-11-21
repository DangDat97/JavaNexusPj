package nasuxjava.webnexus.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.*;
import nasuxjava.webnexus.dto.UserDto;
import nasuxjava.webnexus.entity.User;
import nasuxjava.webnexus.services.UserService;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDto userDto = new UserDto();
        model.addAttribute("UserDto", userDto);
        return "/auth/register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("UserDto") UserDto userDto,
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
            result.rejectValue("email", "error.user", "Email already in use");
        }

        // Kiểm tra các lỗi validation
        if (result.hasErrors()) {
            return "auth/register";
        }

        // Lưu người dùng mới
        try {
            userService.saveUserRegister(userDto);
            return "redirect:/register?success";
        } catch (Exception e) {
            // Xử lý lỗi khi lưu người dùng
            model.addAttribute("errorMessage", "An error occurred while registering. Please try again.");
            return "auth/register";
        }
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

}
