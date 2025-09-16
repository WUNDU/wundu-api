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

import org.springframework.data.domain.Page;

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
    @Operation(summary = "Listar todas as transações do usuário autenticado ou todas se ADMIN")
    public ResponseEntity<List<TransactionResponse>> findAll() {
        List<TransactionResponse> responses = transactionService.findAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/me")
    @Operation(summary = "Listar transações do usuário autenticado")
    public ResponseEntity<List<TransactionResponse>> findByUser() {
        List<TransactionResponse> responses = transactionService.findByUser();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Buscar transação por ID")
    public ResponseEntity<TransactionResponse> findById(@PathVariable String id) {
        TransactionResponse response = transactionService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Listar transações do usuário autenticado por categoria")
    public ResponseEntity<List<TransactionResponse>> findByUserAndCategory(
            @PathVariable String categoryId) {
        List<TransactionResponse> responses = transactionService.findByUserAndCategory(categoryId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/filters")
    @Operation(summary = "Filtrar transações do usuário autenticado ou todas se ADMIN")
    public ResponseEntity<Page<TransactionResponse>> findWithFilters(
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<TransactionResponse> responses =
                transactionService.findWithFilters(categoryId, status, startDate, endDate, page, size);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}/define-category")
    @Operation(summary = "Definir categoria e descrição de uma transação")
    public ResponseEntity<TransactionResponse> defineCategory(
            @PathVariable String id,
            @RequestBody @Valid DefineCategoryRequest request) {
        return ResponseEntity.ok(transactionService.defineTransaction(id, request));
    }
}
