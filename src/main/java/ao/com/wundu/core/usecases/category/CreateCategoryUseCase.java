package ao.com.wundu.core.usecases.category;

import ao.com.wundu.infra.persistence.dtos.CategoryRequest;
import ao.com.wundu.infra.persistence.dtos.CategoryResponse;

public interface CreateCategoryUseCase {
    CategoryResponse execute(CategoryRequest request);
}
