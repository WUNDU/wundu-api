package ao.com.wundu.user_category_limit.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserCategoryLimitResponse(
    @Schema(description = "ID do registro de limite")
    String id,

    @Schema(description = "ID do usuário")
    String userId,

    @Schema(description = "ID da categoria")
    String categoryId,

    @Schema(description = "Limite mensal de gastos")
    Double monthlyLimit
) {}
