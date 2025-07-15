package ao.com.wundu.infra.persistence.controller;

import ao.com.wundu.core.usecases.transaction.CreateTransactionUseCase;
import ao.com.wundu.core.usecases.transaction.ListTransactionUseCase;
import ao.com.wundu.core.usecases.transaction.ListTransactionByUserUseCase;
import ao.com.wundu.infra.persistence.dtos.TransactionRequest;
import ao.com.wundu.infra.persistence.dtos.TransactionResponse;
import ao.com.wundu.infra.persistence.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "WUNDU", description = "Contém todas as operações relativas às transações financeiras (despesas e receitas)")
@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

    @Autowired
    private CreateTransactionUseCase createTransactionUseCase;

    @Autowired
    private ListTransactionUseCase listTransactionUseCase;

    @Autowired
    private ListTransactionByUserUseCase listByUserUseCase;

    @PostMapping
    @Operation(summary = "Criar uma nova transação", description = "Recurso para criar uma nova transação (receita ou despesa)",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Transação criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
                    @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = createTransactionUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar todas as transações", description = "Lista completa de todas as transações do sistema")
    public ResponseEntity<List<TransactionResponse>> findAll() {
        List<TransactionResponse> responses = listTransactionUseCase.execute();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Listar transações por usuário", description = "Lista de todas as transações realizadas por um usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    public ResponseEntity<List<TransactionResponse>> findByUser(@PathVariable String userId) {
        List<TransactionResponse> responses = listByUserUseCase.execute(userId);
        return ResponseEntity.ok(responses);
    }
}
