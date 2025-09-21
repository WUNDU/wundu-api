package ao.com.wundu.goals.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record GoalProgressDTO(

    @Schema(description = "ID do registro", example = "uuid-123")
    String id,

    @Schema(description = "Valor adicionado no progresso", example = "200.00")
    BigDecimal amount,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Data do progresso", example = "2025-09-19")
    LocalDate progressDate,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Data de criação do registro", example = "2025-09-19T15:00:00")
    LocalDateTime createdAt

) {}
