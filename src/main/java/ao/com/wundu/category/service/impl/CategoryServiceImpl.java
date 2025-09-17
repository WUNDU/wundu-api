package ao.com.wundu.category.service.impl;

import ao.com.wundu.category.dto.CategoryRequest;
import ao.com.wundu.category.dto.CategoryResponse;
import ao.com.wundu.category.entity.Category;
import ao.com.wundu.category.mapper.CategoryMapper;
import ao.com.wundu.category.repository.CategoryRepository;
import ao.com.wundu.category.service.CategoryService;
import ao.com.wundu.exception.BusinessException;
import ao.com.wundu.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {

        // TODO: Verificar o tipo de Exception, ResourceNotFound não é a ideal
        if (this.categoryRepository.existsByName(request.name()))
            throw new ResourceNotFoundException( "Categoria já existe: " + request.name(), HttpStatus.CONFLICT);

        try {

            Category category = categoryMapper.toEntity(request);
            category = categoryRepository.save(category);
            return categoryMapper.toResponse(category);

        }
        catch (BusinessException e) {

            //TODO: Padronizar as msgs de erro no futuro
            throw new BusinessException("Ocorreu um erro ao gravar a categoria");
        }
    }

    @Override
    public List<CategoryResponse> findAll() {

        return categoryMapper.toList(categoryRepository.findAll());
    }

    @Override
    public Page<CategoryResponse> findAll(Specification<Category> spec, Pageable pageable) {

        return categoryMapper.toCategoryResponsePage(categoryRepository.findAll(spec, pageable));
    }
}