package ao.com.wundu.goals.dtos;

import ao.com.wundu.goals.enums.GoalType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalRequestDTO(

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 150)
    @Schema(example = "Viagem a Cabo Verde", description = "Título da meta")
    String title,

    @Schema(example = "Poupança para viagem em família", description = "Descrição da meta")
    String description,

    @NotNull(message = "O tipo da meta é obrigatório")
    @Schema(example = "SHORT_TERM", description = "SHORT_TERM ou LONG_TERM")
    GoalType type,

    @NotNull(message = "O valor alvo é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor alvo deve ser maior que zero")
    @Schema(example = "1500.00", description = "Valor alvo da meta")
    BigDecimal targetAmount,

    @NotNull(message = "Data inicial é obrigatória")
    @Schema(example = "2025-09-01", description = "Data de início")
    LocalDate startDate,

    @NotNull(message = "Data final é obrigatória")
    @Schema(example = "2026-09-01", description = "Data final")
    LocalDate endDate

) {}
