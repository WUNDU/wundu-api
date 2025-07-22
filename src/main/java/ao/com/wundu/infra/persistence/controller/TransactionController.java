package ao.com.wundu.infra.persistence.controller;

import ao.com.wundu.core.usecases.transaction.CreateTransactionUseCase;
import ao.com.wundu.core.usecases.transaction.GetTransactionByIdUseCase;
import ao.com.wundu.core.usecases.transaction.ListTransactionByUserAndCategoryUseCase;
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

@Tag(name = "Transactions", description = "Contém todas as operações relativas às transações financeiras")
@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

        @Autowired
        private CreateTransactionUseCase createTransactionUseCase;

        @Autowired
        private ListTransactionUseCase listTransactionUseCase;

        @Autowired
        private ListTransactionByUserUseCase listByUserUseCase;

        @Autowired
        private GetTransactionByIdUseCase getTransactionByIdUseCase;

        @Autowired
        private ListTransactionByUserAndCategoryUseCase listByUserAndCategoryUseCase;

        @PostMapping
        @Operation(summary = "Criar uma nova transação", description = "Recurso para criar uma nova transação (receita ou despesa)", responses = {
                        @ApiResponse(responseCode = "201", description = "Transação criada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
                        @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        })
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
        @Operation(summary = "Listar transações por usuário", description = "Lista de todas as transações realizadas por um usuário", responses = {
                        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        })
        public ResponseEntity<List<TransactionResponse>> findByUser(@PathVariable String userId) {
                List<TransactionResponse> responses = listByUserUseCase.execute(userId);
                return ResponseEntity.ok(responses);
        }

        @GetMapping("/find/{id}")
        @Operation(summary = "Buscar transação por ID", description = "Retorna uma transação específica com base no ID informado", responses = {
                        @ApiResponse(responseCode = "200", description = "Transação encontrada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Transação não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        })
        public ResponseEntity<TransactionResponse> findById(@PathVariable String id) {
                TransactionResponse response = getTransactionByIdUseCase.execute(id);
                return ResponseEntity.ok(response);
        }

        @GetMapping("/{userId}/category/{categoryId}")
        @Operation(summary = "Listar transações por usuário e categoria", description = "Retorna todas as transações de um usuário que pertencem a uma determinada categoria", responses = {
                        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Usuário ou categoria não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        })
        public ResponseEntity<List<TransactionResponse>> findByUserAndCategory(
                        @PathVariable String userId,
                        @PathVariable String categoryId) {
                List<TransactionResponse> responses = listByUserAndCategoryUseCase.execute(userId, categoryId);
                return ResponseEntity.ok(responses);
        }
}
