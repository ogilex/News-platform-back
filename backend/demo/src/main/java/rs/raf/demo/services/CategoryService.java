package rs.raf.demo.services;

import rs.raf.demo.entities.Category;
import rs.raf.demo.repositories.category.CategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {

    @Inject
    private CategoryRepository repository;

    public Category findById(int categoryId) {
        return repository.findById(categoryId);
    }

    public List<Category> getCategories(int page) {
        return repository.getCategories(page);
    }

    public Category createCategory(String name, String description) {
        return repository.createCategory(name, description);
    }

    public Category updateCategory(int categoryId, String name, String description) {
        return repository.updateCategory(categoryId, name, description);
    }

    public Integer getCategoryCount() {
        return repository.getCategoryCount();
    }

    public void deleteCategory(int categoryId) {
        repository.deleteCategory(categoryId);
    }
}
