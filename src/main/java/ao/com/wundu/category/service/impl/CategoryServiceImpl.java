package ao.com.wundu.category.service.impl;

import ao.com.wundu.category.dto.CategoryRequest;
import ao.com.wundu.category.dto.CategoryResponse;
import ao.com.wundu.category.entity.Category;
import ao.com.wundu.category.mapper.CategoryMapper;
import ao.com.wundu.category.repository.CategoryRepository;
import ao.com.wundu.category.service.CategoryService;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    public CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public CategoryResponse create(CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        category = categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toList(categories);
    }
}
