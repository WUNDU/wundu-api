package ao.com.wundu.auditLogs.dtos;

import ao.com.wundu.auditLogs.enums.ActionState;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record AuditLogResponse(

        @Schema(description = "ID único do log", example = "uuid-123")
        String id,

        @Schema(description = "Usuário que realizou a ação", example = "user-123")
        String actorId,

        @Schema(description = "Usuário alvo da ação", example = "user-456")
        String targetUserId,

        @Schema(description = "Tipo da ação", example = "UPDATE")
        ActionState action,

        @Schema(description = "Campos modificados (JSON)", 
                example = "{\"email\": {\"old\": \"a@x.com\", \"new\": \"b@x.com\"}}")
        String changedFields,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @Schema(description = "Data e hora da criação do log", example = "2025-09-19T22:30:00")
        LocalDateTime createdAt
) {}
