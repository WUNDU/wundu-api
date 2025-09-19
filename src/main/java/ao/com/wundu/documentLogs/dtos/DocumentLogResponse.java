package ao.com.wundu.documentLogs.dtos;

import ao.com.wundu.documentLogs.enums.DocumentLogType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record DocumentLogResponse(

        @Schema(description = "ID do log", example = "uuid-123")
        String id,

        @Schema(description = "Usuário que fez o upload", example = "user-123")
        String actorId,

        @Schema(description = "Nome do arquivo", example = "fatura.pdf")
        String fileName,

        @Schema(description = "Tipo MIME do arquivo", example = "application/pdf")
        String contentType,

        @Schema(description = "Tamanho do arquivo em bytes", example = "204800")
        Long fileSize,

        @Schema(description = "Tipo do log", example = "DOCUMENT_UPLOAD")
        DocumentLogType type,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @Schema(description = "Data/hora de criação do log", example = "2025-09-19T23:00:00")
        LocalDateTime createdAt
) {}
