package ao.com.wundu.infra.presentation.service.category;

import ao.com.wundu.core.usecases.category.ListAllCategoriesUseCase;
import ao.com.wundu.infra.persistence.dtos.CategoryResponse;
import ao.com.wundu.infra.persistence.mappers.CategoryMapper;
import ao.com.wundu.infra.presentation.entities.Category;
import ao.com.wundu.infra.presentation.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllCategoriesImpl implements ListAllCategoriesUseCase {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> execute() {
        List<Category> categories = categoryRepository.findAll();
        return CategoryMapper.toList(categories);
    }
}