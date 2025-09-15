package ao.com.wundu.category.mapper;

import ao.com.wundu.category.dto.CategoryRequest;
import ao.com.wundu.category.dto.CategoryResponse;
import ao.com.wundu.category.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request) {
        return new Category(request.name());
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }

    public List<CategoryResponse> toList(List<Category> categories) {
        return categories.stream().map(user -> toResponse(user)).collect(Collectors.toList());
    }
}