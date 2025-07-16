package ao.com.wundu.infra.presentation.service.category;

import ao.com.wundu.core.usecases.category.CreateCategoryUseCase;
import ao.com.wundu.infra.persistence.dtos.CategoryRequest;
import ao.com.wundu.infra.persistence.dtos.CategoryResponse;
import ao.com.wundu.infra.persistence.mappers.CategoryMapper;
import ao.com.wundu.infra.presentation.entities.Category;
import ao.com.wundu.infra.presentation.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryImpl implements CreateCategoryUseCase {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryResponse execute(CategoryRequest request) {
        Category category = CategoryMapper.toEntity(request);
        category = categoryRepository.save(category);
        return CategoryMapper.toResponse(category);
    }
}
