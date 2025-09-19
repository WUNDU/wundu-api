package ao.com.wundu.documentLogs.dtos;

import ao.com.wundu.documentLogs.enums.DocumentLogType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DocumentLogRequest(

        @NotBlank(message = "O ID do ator é obrigatório")
        @Schema(example = "user-123", description = "Usuário que fez o upload")
        String actorId,

        @NotBlank(message = "O nome do arquivo é obrigatório")
        @Schema(example = "fatura.pdf", description = "Nome do arquivo enviado")
        String fileName,

        @NotBlank(message = "O tipo do arquivo é obrigatório")
        @Schema(example = "application/pdf", description = "Tipo MIME do arquivo")
        String contentType,

        @NotNull(message = "O tamanho do arquivo é obrigatório")
        @Min(value = 1, message = "O tamanho deve ser maior que zero")
        @Schema(example = "204800", description = "Tamanho do arquivo em bytes")
        Long fileSize,

        @NotNull(message = "O tipo do log é obrigatório")
        @Schema(example = "DOCUMENT_UPLOAD", description = "Tipo do log (UPLOAD ou ERROR)")
        DocumentLogType type
) {}
