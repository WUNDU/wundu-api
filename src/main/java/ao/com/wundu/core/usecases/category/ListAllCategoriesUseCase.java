package ao.com.wundu.core.usecases.category;

import ao.com.wundu.infra.persistence.dtos.CategoryRequest;
import ao.com.wundu.infra.persistence.dtos.CategoryResponse;
import java.util.List;

public interface ListAllCategoriesUseCase {
    List<CategoryResponse> execute();
}