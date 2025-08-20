package ao.com.wundu.app.unit.service.category;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

import ao.com.wundu.category.dto.CategoryRequest;
import ao.com.wundu.category.dto.CategoryResponse;
import ao.com.wundu.category.entity.Category;
import ao.com.wundu.category.mapper.CategoryMapper;
import ao.com.wundu.category.repository.CategoryRepository;
import ao.com.wundu.category.service.CategoryService;
import ao.com.wundu.category.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceUnitTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryRequest request;
    private CategoryResponse response;

    @BeforeEach
    public void setup() {
        request = new CategoryRequest(
                "ALIMENTAÇÂO"
        );

        category = new Category();
        response = new CategoryResponse("", "ALIMENTAÇÂO");
    }

    @DisplayName("Junit test for saveCategory method")
    @Test
    public void givenCategoryObject_whenSaveCategory_thenReturnEmployeeObject() {
        // given
        when(categoryMapper.toEntity(request)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toResponse(category)).thenReturn(response);


        // when
        CategoryResponse result = categoryService.create(request);


        // then
        assertNotNull(result);
        assertEquals(response, result);

        verify(categoryMapper).toEntity(request);
        verify(categoryRepository).save(category);
        verify(categoryMapper).toResponse(category);
    }

    @DisplayName("Junit test for findAllCategory method ")
    @Test
    public void givenCategoryList_whenFindAllCategory_thenReturnCategoryList() {

        // given
        List<Category> fakeCategories = List.of(
                new Category("ALIMENTAÇÂO"),
                new Category("TRANSAPORTE")
        );

        List<CategoryResponse> fakeResponses = List.of(
                new CategoryResponse("", "ALIMENTAÇÂO"),
                new CategoryResponse("", "TRANSAPORTE")
        );

        when(categoryRepository.findAll()).thenReturn(fakeCategories);
        when(categoryMapper.toList(fakeCategories)).thenReturn(fakeResponses);

        // when
        List<CategoryResponse> categoryResponses = categoryService.findAll();

        // then
        assertEquals(2, categoryResponses.size());
        assertEquals("ALIMENTAÇÂO", categoryResponses.get(0).name());
    }

    @DisplayName("Junit test for findAllCategory method (negative scenario) ")
    @Test
    public void givenEmptyCategoriesList_whenFinAllCategories_thenReturnEmptyCategoriesList() {

        // Given
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<CategoryResponse> categoryResponses = categoryService.findAll();

        assertThat(categoryResponses).isEmpty();
        assertThat(categoryResponses.size()).isEqualTo(0);
    }
}
