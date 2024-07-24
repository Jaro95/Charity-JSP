package pl.coderslab.charity.domain.category;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/rest/controller")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategory(@PathVariable Long id) {
        return new CategoryResponse(categoryService.getCategory(id));
    }

    @PostMapping("/add")
    public String addCategory(@RequestBody Optional<CategoryRequest> categoryRequest) {
        return categoryService.addCategory(categoryRequest);
    }

    @PutMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id, @RequestBody Optional<CategoryRequest> categoryRequest) {
        return categoryService.updateCategory(id,categoryRequest);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}
