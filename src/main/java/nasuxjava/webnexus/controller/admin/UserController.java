package nasuxjava.webnexus.controller.admin;

import nasuxjava.webnexus.dto.UserDto;
import nasuxjava.webnexus.entity.User;
import nasuxjava.webnexus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RequestMapping({ "/Admin/Users",
        "/Admin/users" })
@Controller
public class UserController {

    private static final String LAYOUT = "admin/template";
    private static final String PATH = "/admin/user";
    private static final String REDIRECTLIST = "redirect:/Admin/Users";

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("content", PATH + "/list.html");
        return LAYOUT;
    }

    @GetMapping({ "/Add", "/add" })
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("content", PATH + "/add.html");
        return LAYOUT;
    }

    @PostMapping({ "/Add", "/add" })
    public String addUser(@Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        if (existingUser != null) {
            result.rejectValue("email", "error.user", "Email already in use");
            return REDIRECTLIST;
        }
        userService.saveUser(userDto);
        return REDIRECTLIST;
    }

    @GetMapping("/Edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("userDto", user);
        model.addAttribute("content", PATH + "/edit.html");
        return LAYOUT; // Trả về view hiển thị form sửa người dùng
    }

    @PostMapping("/Edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDto userDto) {
        userDto.setId(id); // Đặt ID cho userDto
        userService.saveUser(userDto);
        return REDIRECTLIST; // Chuyển hướng về danh sách người dùng sau khi sửa
    }

    @GetMapping("/Delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return REDIRECTLIST; // Chuyển hướng về danh sách người dùng sau khi xóa
    }
}