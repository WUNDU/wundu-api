package ao.com.wundu.user_category_limit.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCategoryLimitRequest(
    @NotBlank String userId,
    @NotBlank String categoryId,
    @NotNull Double monthlyLimit
) {}
