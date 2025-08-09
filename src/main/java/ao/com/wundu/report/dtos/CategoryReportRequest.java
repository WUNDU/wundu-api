package ao.com.wundu.report.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryReportRequest(
    @NotBlank String userId,
    @NotBlank String categoryId,
    @NotNull Integer year,
    @NotNull Integer month
) {}
