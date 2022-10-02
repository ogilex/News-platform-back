package rs.raf.demo.repositories.category;

import rs.raf.demo.entities.Category;

import java.util.List;

public interface CategoryRepository {

    Category findById(int categoryId);

    List<Category> getCategories(int page);

    Category createCategory(String name, String description);

    Category updateCategory(int categoryId, String name, String description);

    Integer getCategoryCount();

    void deleteCategory(int categoryId);
}
