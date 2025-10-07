package ao.com.wundu.document.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record DocumentListDTO(

        @Schema(description = "ID do documento")
        String id,

        @Schema(description = "Nome do arquivo original")
        String fileName,

        @Schema(description = "Tipo de conteúdo")
        String contentType,

        @Schema(description = "Tamanho em bytes")
        Long fileSize,

        @Schema(description = "Status atual", example = "pending")
        String status,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt
) {
}
