package pl.coderslab.charity.domain.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    public String addCategory(Optional<CategoryRequest> categoryRequest) {
        categoryRequest.ifPresent(c -> {
            categoryRepository.save(Category.builder()
                    .name(categoryRequest.get().name())
                    .build());
            log.info("Added new category:\n{}\n{}", categoryRequest.get().name());
        });
        return categoryRequest.isPresent() ? "Added new category:\n" + categoryRequest.get().name()
        : "Category not added";
    }

    public String updateCategory(Long id, Optional<CategoryRequest> categoryRequest) {
        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(c -> {
            categoryRequest.ifPresent(cr -> {
                if (cr.name() != null) {
                    c.setName(cr.name());
                }
                categoryRepository.save(c);
                log.info("Updated category:\n {}",c.toString());
            });
        });
        return category.isPresent() && categoryRequest.isPresent() ? "Updated category:\n" + category.get().getName()
                : "The category to update was not found";
    }

    public String deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(c -> {
            categoryRepository.delete(c);
            log.info("Deleted category:\n {}",c.toString());
        });
        return category.isPresent() ? "Deleted category:\n" + category.get().getName()
                :"The category to delete was not found";
    }
}
