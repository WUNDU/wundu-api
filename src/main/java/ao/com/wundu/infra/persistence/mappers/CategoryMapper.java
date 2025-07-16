package ao.com.wundu.infra.persistence.mappers;

import ao.com.wundu.infra.persistence.dtos.CategoryRequest;
import ao.com.wundu.infra.persistence.dtos.CategoryResponse;
import ao.com.wundu.infra.presentation.entities.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {
    public static Category toEntity(CategoryRequest request) {
        return new Category(request.name(), request.description());
    }

    public static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }

    public static List<CategoryResponse> toList(List<Category> categories) {
        return categories.stream().map(CategoryMapper::toResponse).collect(Collectors.toList());
    }
}