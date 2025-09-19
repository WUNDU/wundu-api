package ao.com.wundu.documentLogs.controller;

import ao.com.wundu.documentLogs.dtos.DocumentLogRequest;
import ao.com.wundu.documentLogs.dtos.DocumentLogResponse;
import ao.com.wundu.documentLogs.service.DocumentLogService;
import ao.com.wundu.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Document Logs", description = "Auditoria de uploads de documentos")
@RestController
@RequestMapping("api/v1/document-logs")
public class DocumentLogController {

    @Autowired
    private DocumentLogService service;

    @PostMapping
    @Operation(summary = "Registrar novo log de documento")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Log criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentLogResponse.class))),
        @ApiResponse(responseCode = "422", description = "Dados inválidos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<DocumentLogResponse> create(@Valid @RequestBody DocumentLogRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar log por ID")
    public ResponseEntity<DocumentLogResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os logs")
    public ResponseEntity<List<DocumentLogResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/actor/{actorId}")
    @Operation(summary = "Listar logs por ator")
    public ResponseEntity<List<DocumentLogResponse>> findByActor(@PathVariable String actorId) {
        return ResponseEntity.ok(service.findByActor(actorId));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Listar logs por tipo (UPLOAD ou ERROR)")
    public ResponseEntity<List<DocumentLogResponse>> findByType(@PathVariable String type) {
        return ResponseEntity.ok(service.findByType(type));
    }
}
