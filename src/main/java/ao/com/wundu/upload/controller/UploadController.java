package ao.com.wundu.upload.controller;

import ao.com.wundu.upload.dtos.UploadFileResponse;
import ao.com.wundu.upload.service.UploadService;
import ao.com.wundu.exception.ErrorMessage;
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
@RequestMapping("/api/v1/uploads")
@Tag(name = "Upload", description = "Upload de arquivos como faturas e imagens")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload e salvamento do arquivo",
               description = "Recebe um arquivo, salva e retorna as informações básicas do mesmo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Arquivo salvo com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UploadFileResponse.class))),
        @ApiResponse(responseCode = "400", description = "Arquivo inválido",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<UploadFileResponse> upload(
        @RequestParam("file") MultipartFile file
    ) {
        UploadFileResponse response = uploadService.uploadFile(file);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/ocr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload para OCR",
               description = "Faz upload sem salvar no storage, apenas retorna os bytes do arquivo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Arquivo processado com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)),
        @ApiResponse(responseCode = "400", description = "Arquivo inválido",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<byte[]> uploadOcr(
        @RequestParam("file") MultipartFile file
    ) {
        byte[] fileBytes = uploadService.uploadOcr(file);
        return ResponseEntity.ok(fileBytes);
    }
}
