package ao.com.wundu.ocr.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OcrResponse(

        @Schema(description = "Identificador único do OCR", example = "123e4567-e89b-12d3-a456-426614174000")
        @NotBlank(message = "O ID não pode ser vazio")
        String id,

        @Schema(description = "Identificador do usuário dono do OCR", example = "user-001")
        @NotBlank(message = "O userId não pode ser vazio")
        String userId,

        @Schema(description = "Nome do ficheiro processado", example = "documento.pdf")
        @NotBlank(message = "O nome do ficheiro não pode ser vazio")
        String fileName,

        @Schema(description = "Tipo de conteúdo do ficheiro", example = "application/pdf")
        @NotBlank(message = "O contentType não pode ser vazio")
        String contentType,

        @Schema(description = "Tamanho do ficheiro em bytes", example = "204800")
        @Positive(message = "O tamanho do ficheiro deve ser maior que zero")
        long fileSize,

        @Schema(description = "Texto extraído do OCR", example = "Exemplo de texto processado")
        @NotBlank(message = "O texto não pode ser vazio")
        String text,

        @Schema(description = "Status do processamento OCR", example = "SUCCESS")
        @NotBlank(message = "O status não pode ser vazio")
        String status
) {}
