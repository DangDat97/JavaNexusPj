package nasuxjava.webnexus.services;

import nasuxjava.webnexus.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category saveCategory(Category category);

    void deleteCategory(Long id);
}