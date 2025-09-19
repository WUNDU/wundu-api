package ao.com.wundu.auditLogs.controller;

import ao.com.wundu.auditLogs.dtos.AuditLogRequest;
import ao.com.wundu.auditLogs.dtos.AuditLogResponse;
import ao.com.wundu.auditLogs.service.AuditLogService;
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

@Tag(name = "Audit Logs", description = "Histórico de ações (somente criação e consulta)")
@RestController
@RequestMapping("api/v1/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @PostMapping
    @Operation(summary = "Criar um novo registro de auditoria")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Log criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuditLogResponse.class))),
        @ApiResponse(responseCode = "422", description = "Dados inválidos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<AuditLogResponse> create(@Valid @RequestBody AuditLogRequest request) {
        AuditLogResponse response = auditLogService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar log por ID")
    public ResponseEntity<AuditLogResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(auditLogService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os logs")
    public ResponseEntity<List<AuditLogResponse>> findAll() {
        return ResponseEntity.ok(auditLogService.findAll());
    }

    @GetMapping("/actor/{actorId}")
    @Operation(summary = "Listar logs por ator")
    public ResponseEntity<List<AuditLogResponse>> findByActor(@PathVariable String actorId) {
        return ResponseEntity.ok(auditLogService.findByActor(actorId));
    }

    @GetMapping("/target/{targetUserId}")
    @Operation(summary = "Listar logs por usuário alvo")
    public ResponseEntity<List<AuditLogResponse>> findByTarget(@PathVariable String targetUserId) {
        return ResponseEntity.ok(auditLogService.findByTarget(targetUserId));
    }
}
