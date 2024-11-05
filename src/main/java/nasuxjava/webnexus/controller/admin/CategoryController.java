package nasuxjava.webnexus.controller.admin;

import nasuxjava.webnexus.entity.Category;
import nasuxjava.webnexus.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Controller
public class CategoryController {

    private static final String LAYOUT = "admin/template";
    private static final String PATH = "/admin/category";
    private static final String REDIRECTLIST = "redirect:/Admin/Category";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/Admin/Category")
    public String listUsers(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("title", "List Category");
        model.addAttribute("Category", new Category());
        model.addAttribute("categories", categories);
        model.addAttribute("content", PATH + "/list");
        return LAYOUT;
    }

    @GetMapping({ "/Admin/Category/Add" })
    public String showAddCategoryForm(Model model) {
        model.addAttribute("title", "Add New Category");
        model.addAttribute("Category", new Category());
        model.addAttribute("content", PATH + "/add.html");
        return LAYOUT;
    }

    @PostMapping({ "/Admin/Category/add" })
    public String c(@Valid @ModelAttribute("category") Category Category,
            BindingResult result,
            Model model) {

        Category existingCategory = categoryService.getCategoryByName(Category.getName());
        if (existingCategory != null) {
            result.rejectValue("name", "error.category", "Category already in use");
            return REDIRECTLIST;
        }
        categoryService.saveCategory(Category);
        return REDIRECTLIST;
    }

    @GetMapping("/Admin/Category/Edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Edit Category");
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            model.addAttribute("error", "Category already in use");
            return REDIRECTLIST;
        }
        model.addAttribute("category", category);
        model.addAttribute("content", PATH + "/edit.html");
        return LAYOUT;
    }

    @PostMapping("/Admin/Category/Edit/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category) {
        category.setId(id);
        categoryService.saveCategory(category);
        return REDIRECTLIST;
    }

    @GetMapping("/Admin/Category/Delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return REDIRECTLIST;
    }
}