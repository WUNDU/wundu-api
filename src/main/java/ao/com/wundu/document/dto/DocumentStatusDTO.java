package ao.com.wundu.document.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record DocumentStatusDTO(
        @Schema(description = "ID do documento")
        String id,

        @Schema(description = "Nome do arquivo")
        String fileName,

        //  TODO: Adicionar o DocumentStatus em vez de uma String
        @Schema(description = "Status atual", example = "pending")
        String status,

        @Schema(description = "Tamanho do arquivo")
        Long fileSize,

        @Schema(description = "Texto extraído (se disponivel)")
        String extractedText,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @Schema(description = "Data do upload")
        LocalDateTime createdAt
) {
}
