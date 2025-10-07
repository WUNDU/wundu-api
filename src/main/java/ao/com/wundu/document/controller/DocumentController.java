package ao.com.wundu.document.controller;

import ao.com.wundu.document.dto.DocumentListDTO;
import ao.com.wundu.document.dto.DocumentStatusDTO;
import ao.com.wundu.document.dto.UploadResponseDTO;
import ao.com.wundu.document.service.DocumentService;
import ao.com.wundu.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@Tag(name = "Documents", description = "UC05: Upload de Documentos - Sistema de upload de PDFs e imagens para OCR")
public class DocumentController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload de documento",
            description = "Faz upload de documento financeiro (PDF, JPG, PNG) para processamento OCR conforme UC05")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Upload realizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UploadResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Arquivo inválido (formato/tamanho)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Arquivo duplicado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Falha no salvamento (disco/bucket)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<UploadResponseDTO> uploadDocument(@RequestParam("file") MultipartFile file) {
        logger.info("Recebida requisição de upload: {}", file.getOriginalFilename());

        UploadResponseDTO response = documentService.uploadResponseDTO(file);

        logger.info("Upload processado com sucesso - ID: {}", response.documentId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    @Operation(summary = "Listar documentos",
            description = "Lista todos os documentos do usuário autenticado ordenados por data")
    @ApiResponse(responseCode = "200", description = "Lista de documentos retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DocumentListDTO.class)))
    public ResponseEntity<List<DocumentListDTO>> listDocuments() {
        List<DocumentListDTO> documents = documentService.listDocuments();
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar status de documento",
            description = "Retorna detalhes e status atual de um documento específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status do documento retornado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DocumentStatusDTO.class))),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<DocumentStatusDTO> getDocumentStatus(@PathVariable String id) {
        logger.debug("Consultando status do documento: {}", id);

        DocumentStatusDTO status = documentService.getDocumentStatus(id);
        return ResponseEntity.ok(status);
    }


    @GetMapping("/status/{status}")
    @Operation(summary = "Filtrar documentos por status",
            description = "Lista documentos filtrados por status (pending, processed, error)")
    public ResponseEntity<List<DocumentListDTO>> getDocumentsByStatus(@PathVariable String status) {
        List<DocumentListDTO> documents = documentService.getDocumentsByStatus(status);
        return ResponseEntity.ok(documents);
    }


    @GetMapping("/count")
    @Operation(summary = "Contar documentos",
            description = "Retorna total de documentos do usuário autenticado")
    public ResponseEntity<Long> countUserDocuments() {
        // Obtém ID do usuário autenticado através do service
        long count = documentService.countUserDocuments(null); // Service vai pegar o user do contexto
        return ResponseEntity.ok(count);
    }
}
