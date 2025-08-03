package ao.com.wundu.category.service;

import ao.com.wundu.category.dto.CategoryRequest;
import ao.com.wundu.category.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);
    List<CategoryResponse> findAll();
}
