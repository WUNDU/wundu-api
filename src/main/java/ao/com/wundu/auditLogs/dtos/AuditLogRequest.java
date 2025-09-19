package ao.com.wundu.auditLogs.dtos;

import ao.com.wundu.auditLogs.enums.ActionState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuditLogRequest(

        @NotBlank(message = "O ID do ator é obrigatório")
        @Schema(example = "user-123", description = "Usuário que realizou a ação")
        String actorId,

        @Schema(example = "user-456", description = "Usuário alvo da ação (pode ser nulo)")
        String targetUserId,

        @NotNull(message = "A ação é obrigatória")
        @Schema(example = "UPDATE", description = "Tipo da ação (CREATE, UPDATE, DEACTIVATE, REACTIVATE)")
        ActionState action,

        @Schema(example = "{\"email\": {\"old\": \"a@x.com\", \"new\": \"b@x.com\"}}",
                description = "Campos modificados em formato JSON")
        String changedFields
) {}
