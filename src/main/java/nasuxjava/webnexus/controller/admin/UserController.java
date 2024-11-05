package nasuxjava.webnexus.controller.admin;

import nasuxjava.webnexus.dto.UserDto;
import nasuxjava.webnexus.dto.UserFilterDto;
import nasuxjava.webnexus.entity.User;
import nasuxjava.webnexus.repository.UserFilterResposotory;
import nasuxjava.webnexus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/Admin/Users")
@Controller
public class UserController {

    private static final String LAYOUT = "admin/template";
    private static final String PATH = "/admin/user";
    private static final String REDIRECTLIST = "redirect:/Admin/Users";
    private static final String REDIRECTADD = "redirect:/Admin/Users/Add";
    private static final String REDIRECTEDIT = "redirect:/Admin/Users/Edit";

    @Autowired
    private UserService userService;

    @Autowired
    private UserFilterResposotory userFilterResposotory;

    @Autowired
    private HttpSession session;

    // @GetMapping
    // public String listUsers(Model model) {
    // List<UserDto> users = userService.getAllUsers();
    // UserFilterDto userFilterDto = new UserFilterDto();
    // model.addAttribute("userFilterDto", userFilterDto);
    // model.addAttribute("users", users);
    // model.addAttribute("title", "List Users");
    // model.addAttribute("content", PATH + "/list.html");
    // return LAYOUT;
    // }

    @GetMapping
    public String listUsers(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size, Model model) {
        Page<User> userPage = userService.findPaginated(page, size);
        List<UserDto> users = userService.convertListEntityToDto(userPage.getContent());
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("userFilterDto", new UserFilterDto());
        model.addAttribute("users", users);
        model.addAttribute("title", "List Users");
        model.addAttribute("content", PATH + "/list.html");
        return LAYOUT;
    }

    @GetMapping("/Filter")
    public String filterUser(@ModelAttribute("userFilterDto") UserFilterDto userFilterDto,
            Model model) {
        Pageable pageable = PageRequest.of(userFilterDto.getPage(), userFilterDto.getSize());
        List<UserDto> listUserDto = new ArrayList<>();
        // try {
        // Page<User> users =
        // userFilterResposotory.findFilteredUsers(userFilterDto.getFullName(),
        // userFilterDto.getEmail(), userFilterDto.getPhone(), pageable);

        // listUserDto = userService.convertListEntityToDto(users.getContent());
        // model.addAttribute("totalPages", users.getTotalPages());
        // } catch (Exception e) {
        // session.setAttribute("message.error", e.getMessage());
        // return REDIRECTLIST;
        // }
        model.addAttribute("users", listUserDto);

        model.addAttribute("currentPage", userFilterDto.getPage());
        return REDIRECTLIST;
    }

    @GetMapping("/Add")
    public String showAddUserForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("title", "Add Users");
        model.addAttribute("content", PATH + "/add.html");
        return LAYOUT;
    }

    @PostMapping("/Add")
    public String addUser(@Valid @ModelAttribute("userDto") UserDto userDto,
            BindingResult result,
            Model model) {
        // check email for duplicate
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        // If there is already a return message page
        if (existingUser != null) {
            session.setAttribute("message.error", "Email already exists");
            return REDIRECTADD;
        }
        try {
            userService.saveUser(userDto);
        } catch (Exception e) {
            session.setAttribute("message.error", e.getMessage());
            return REDIRECTADD;
        }
        session.setAttribute("message.success", "New user added successfully");
        return REDIRECTADD;
    }

    @GetMapping("/Edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        UserDto userDto = userService.convertEntityToDto(user);
        model.addAttribute("title", "Edit Users");
        model.addAttribute("userDto", userDto);
        model.addAttribute("content", PATH + "/edit.html");
        return LAYOUT; // Trả về view hiển thị form sửa người dùng
    }

    @GetMapping("/Detail/{id}")
    public String showDetailUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        UserDto userDto = userService.convertEntityToDto(user);
        model.addAttribute("title", "Details User");
        model.addAttribute("userDto", userDto);
        model.addAttribute("content", PATH + "/detail.html");
        return LAYOUT; // Trả về view hiển thị form sửa người dùng
    }

    @PostMapping("/Edit/{id}")
    public String updateUser(@Valid @ModelAttribute("userDto") UserDto userDto,
            @PathVariable Long id,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            session.setAttribute("message.error", result.getAllErrors());
            return REDIRECTEDIT + "/" + id;
        }
        // User user = userService.getUserById(id).orElseThrow();
        // if (user.getPassword().equals(userDto.getPassword()) != true &&
        // userDto.getPassword() != null) {
        // userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // }
        // user = userService.convertDtoToEntity(userDto, user);
        userDto.setId(id);
        try {
            userService.updateUser(userDto);
        } catch (Exception e) {
            session.setAttribute("message.error", e.getMessage());
            return REDIRECTEDIT + "/" + id;
        }
        session.setAttribute("message.success", "Update User Success");
        return REDIRECTEDIT + "/" + id; // Chuyển hướng về danh sách người dùng sau khi sửa
    }

    @GetMapping("/Delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            session.setAttribute("message.error", e.getMessage());
            return REDIRECTEDIT + "/" + id;
        }

        return REDIRECTLIST; // Chuyển hướng về danh sách người dùng sau khi xóa
    }
}