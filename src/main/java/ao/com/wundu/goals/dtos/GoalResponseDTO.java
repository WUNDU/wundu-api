package ao.com.wundu.goals.dtos;

import ao.com.wundu.goals.enums.GoalStatus;
import ao.com.wundu.goals.enums.GoalType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record GoalResponseDTO(

    @Schema(description = "ID da meta", example = "uuid-goal-1")
    String id,

    @Schema(description = "ID do usuário dono da meta", example = "user-123")
    String userId,

    @Schema(description = "Título da meta", example = "Viagem")
    String title,

    @Schema(description = "Descrição da meta", example = "Poupança para viagem")
    String description,

    @Schema(description = "Tipo da meta", example = "SHORT_TERM")
    GoalType type,

    @Schema(description = "Valor alvo", example = "5000.00")
    BigDecimal targetAmount,

    @Schema(description = "Valor atual acumulado", example = "1200.00")
    BigDecimal currentAmount,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate startDate,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate endDate,

    @Schema(description = "Status da meta", example = "ACTIVE")
    GoalStatus status,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime createdAt,

    List<GoalProgressDTO> progress

) {}
