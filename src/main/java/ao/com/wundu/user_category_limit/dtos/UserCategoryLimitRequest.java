package ao.com.wundu.user_category_limit.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCategoryLimitRequest(
    @NotBlank(message = "O ID do usuário é obrigatório")
    @Schema(description = "ID do usuário", example = "12345-abcde")
    String userId,

    @NotBlank(message = "O ID da categoria é obrigatório")
    @Schema(description = "ID da categoria", example = "c5b6199b-7d67-47a6-9de7-180885b6d34c")
    String categoryId,

    @NotNull(message = "O limite mensal é obrigatório")
    @Schema(description = "Limite mensal de gastos", example = "1000.0")
    Double monthlyLimit
) {}
