package ao.com.wundu.infra.persistence.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DefineCategoryRequest(

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    @Schema(description = "Nova descrição da transação", example = "Salário recebido no início do mês")
    String description,

    @NotBlank(message = "O nome da categoria é obrigatório")
    @Schema(description = "Nome da nova ou existente categoria", example = "Salário")
    String categoryName
) {}
