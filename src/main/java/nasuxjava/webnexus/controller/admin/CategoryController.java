package nasuxjava.webnexus.controller.admin;

import nasuxjava.webnexus.entity.Category;
import nasuxjava.webnexus.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.List;

@RequestMapping("/Admin/Category")
@Controller
public class CategoryController {

    private static final String LAYOUT = "admin/template";
    private static final String PATH = "/admin/category";
    private static final String REDIRECTLIST = "redirect:/Admin/Category";

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private HttpSession session;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("urlForm", "Add");
        model.addAttribute("urlDelete", "Category");
        model.addAttribute("title", "List Category");
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categories);
        model.addAttribute("content", PATH + "/list");
        return LAYOUT;
    }

    // @GetMapping({ "/Add" })
    // public String showAddCategoryForm(Model model) {
    // model.addAttribute("title", "Add New Category");
    // model.addAttribute("Category", new Category());
    // model.addAttribute("content", PATH + "/add.html");
    // return LAYOUT;
    // }

    @PostMapping({ "/Add" })
    public String categoryAdd(@Valid @ModelAttribute("category") Category category,
            BindingResult result,
            Model model) {

        Category existingCategory = categoryService.getCategoryByName(category.getName());
        if (existingCategory != null) {
            // result.rejectValue("name", "error.category", "Category already in use");
            session.setAttribute("message.error", "Category already in use");
            return REDIRECTLIST;
        }
        try {
            categoryService.saveCategory(category);
        } catch (Exception e) {
            session.setAttribute("message.error", "An error occurred while adding Category.");
        }

        session.setAttribute("message.success", "New Category added successfully");
        return REDIRECTLIST;
    }

    @GetMapping("/Edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("title", "Edit Category");
        model.addAttribute("urlForm", "Edit/" + id);
        model.addAttribute("urlDelete", "Category");
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            session.setAttribute("message.error", "Category already in use");
            return REDIRECTLIST;
        }
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        model.addAttribute("content", PATH + "/list.html");
        return LAYOUT;
    }

    @PostMapping("/Edit/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category) {
        category.setId(id);
        try {
            categoryService.saveCategory(category);
        } catch (Exception e) {
            session.setAttribute("message.error", "There was an error when editing Category.");
        }
        session.setAttribute("message.success", "Edit category successfully");
        return REDIRECTLIST;
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}