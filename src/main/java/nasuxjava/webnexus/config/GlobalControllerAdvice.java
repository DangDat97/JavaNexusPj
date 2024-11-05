package nasuxjava.webnexus.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import nasuxjava.webnexus.entity.Category;
import nasuxjava.webnexus.entity.User;
import nasuxjava.webnexus.services.CategoryService;
import nasuxjava.webnexus.services.UserService;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    // @ModelAttribute("loggedInUser")
    // public UserDetails getLoggedInUser(HttpSession session) {

    // return (UserDetails) session.getAttribute("loggedInUser");
    // }

    @ModelAttribute("infoUserLogin")
    public User getInfoUserLogin() {
        User user = new User();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            user = userService.findUserByEmail(userDetails.getUsername());
            return user;
        }
        return user;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }
}