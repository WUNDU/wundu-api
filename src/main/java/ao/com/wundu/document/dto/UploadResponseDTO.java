package ao.com.wundu.document.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UploadResponseDTO(

        @Schema(description = "ID único do documento", example = "abs123")
        String documentId,

        //  TODO: Adicionar o DocumentStatus em vez de uma String ou permanecer assim
        @Schema(description = "Status do processamento", example = "pending")
        String status,

        @Schema(description = "Mensagem de confirmação", example = "Upload realizado com sucesso")
        String message
) {
}
