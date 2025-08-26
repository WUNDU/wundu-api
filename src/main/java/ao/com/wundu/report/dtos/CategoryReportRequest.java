package ao.com.wundu.report.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CategoryReportRequest(
    @NotBlank(message = "O ID do usuário é obrigatório")
    String userId,

    @NotBlank(message = "O ID da categoria é obrigatório")
    String categoryId,

    @NotBlank(message = "A data é obrigatória")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",
             message = "A data deve estar no formato YYYY-MM-DD")
    String date
) {}
