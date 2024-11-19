package nasuxjava.webnexus.services;

import nasuxjava.webnexus.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    List<Category> getCategoriesById(List<Long> listIdCategory);

    Category getCategoryById(Long id);

    Category getCategoryByName(String nameCategory);

    Category saveCategory(Category category);

    void deleteCategory(Long id);
}