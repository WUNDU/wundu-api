package ao.com.wundu.transaction.controller;

import ao.com.wundu.category.dto.DefineCategoryRequest;
import ao.com.wundu.exception.ErrorMessage;
import ao.com.wundu.transaction.dtos.TransactionRequest;
import ao.com.wundu.transaction.dtos.TransactionResponse;
import ao.com.wundu.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Transactions", description = "Contém todas as operações relativas às transações financeiras")
@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Criar uma nova transação", description = "Recurso para criar uma nova transação (receita ou despesa)")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Transação criada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
        @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar todas as transações", description = "Lista completa de todas as transações do sistema")
    public ResponseEntity<List<TransactionResponse>> findAll() {
        List<TransactionResponse> responses = transactionService.findAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Listar transações por usuário", description = "Lista de todas as transações realizadas por um usuário")
    public ResponseEntity<List<TransactionResponse>> findByUser(@PathVariable String userId) {
        List<TransactionResponse> responses = transactionService.findByUser(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Buscar transação por ID", description = "Retorna uma transação específica com base no ID informado")
    public ResponseEntity<TransactionResponse> findById(@PathVariable String id) {
        TransactionResponse response = transactionService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/category/{categoryId}")
    @Operation(summary = "Listar transações por usuário e categoria",
               description = "Retorna todas as transações de um usuário que pertencem a uma determinada categoria")
    public ResponseEntity<List<TransactionResponse>> findByUserAndCategory(
            @PathVariable String userId,
            @PathVariable String categoryId) {
        List<TransactionResponse> responses = transactionService.findByUserAndCategory(userId, categoryId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{userId}/filters")
    @Operation(summary = "Filtrar transações",
               description = "Permite combinar múltiplos filtros opcionais: intervalo de datas, categoria e status")
    public ResponseEntity<List<TransactionResponse>> findWithFilters(
            @PathVariable String userId,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TransactionResponse> responses =
                transactionService.findWithFilters(userId, categoryId, status, startDate, endDate);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}/define-category")
    @Operation(summary = "Definir categoria e descrição de uma transação",
               description = "Atualiza a descrição e associa uma categoria à transação. Se a categoria não existir, será criada automaticamente.")
    public ResponseEntity<TransactionResponse> defineCategory(
            @PathVariable String id,
            @RequestBody @Valid DefineCategoryRequest request) {
        return ResponseEntity.ok(transactionService.defineTransaction(id, request));
    }
}
