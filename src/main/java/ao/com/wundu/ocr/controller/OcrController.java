package ao.com.wundu.ocr.controller;

import ao.com.wundu.exception.ErrorMessage;
import ao.com.wundu.ocr.dto.OcrResponse;
import ao.com.wundu.ocr.service.OcrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/ocr")
@Tag(name = "OCR", description = "Operações OCR do arquivo selecionado")
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Processar OCR no arquivo enviado", description = "Valida via UploadService, envia para o modelo e retorna texto completo.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Texto extraído com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OcrResponse.class))),
        @ApiResponse(responseCode = "400", description = "Arquivo inválido ou tipo não suportado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "500", description = "Erro no serviço OCR", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<OcrResponse> process(@RequestParam("file") MultipartFile file) {
        OcrResponse response = ocrService.processOcr(file);
        return ResponseEntity.ok(response);
    }
}
