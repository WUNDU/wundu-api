package ao.com.wundu.category.service;

import ao.com.wundu.category.dto.CategoryRequest;
import ao.com.wundu.category.dto.CategoryResponse;
import ao.com.wundu.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);
    List<CategoryResponse> findAll();
    Page<CategoryResponse> findAll(Specification<Category> spec, Pageable pageable);

}
