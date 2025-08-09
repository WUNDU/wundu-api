package ao.com.wundu.upload.controller;

import ao.com.wundu.upload.dtos.UploadFileResponse;
import ao.com.wundu.upload.service.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
        summary = "Faz o upload de um arquivo PDF ou imagem",
        description = "Aceita arquivos PDF, JPEG ou PNG. Retorna uma URL de acesso.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Upload realizado com sucesso",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UploadFileResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Arquivo inválido ou ausente",
                content = @Content
            )
        }
    )
    public ResponseEntity<UploadFileResponse> upload(
        @Parameter(
            description = "Arquivo para upload (PDF, PNG ou JPEG)",
            required = true,
            content = @Content(
                mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                schema = @Schema(type = "string", format = "binary")
            )
        )
        @RequestParam("file") MultipartFile file
    ) {
        UploadFileResponse response = uploadService.uploadFile(file);
        return ResponseEntity.ok(response);
    }
}
