package ao.com.wundu.user_category_limit.controller;

import ao.com.wundu.exception.ErrorMessage;
import ao.com.wundu.user_category_limit.dtos.UserCategoryLimitRequest;
import ao.com.wundu.user_category_limit.dtos.UserCategoryLimitResponse;
import ao.com.wundu.user_category_limit.service.UserCategoryLimitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Limits", description = "Definição e consulta de limites mensais por categoria para cada usuário")
@RestController
@RequestMapping("api/v1/limits")
public class UserCategoryLimitController {

    @Autowired
    private UserCategoryLimitService limitService;

    @Operation(summary = "Definir limite mensal para uma categoria", description = "Define o limite de gastos mensal para uma categoria específica de um usuário",
        responses = {
            @ApiResponse(responseCode = "200", description = "Limite definido com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCategoryLimitResponse.class))),
            @ApiResponse(responseCode = "422", description = "Dados inválidos",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        }
    )
    @PostMapping
    public ResponseEntity<UserCategoryLimitResponse> setLimit(@Valid @RequestBody UserCategoryLimitRequest request) {
        return ResponseEntity.ok(limitService.setLimit(request));
    }

    @Operation(summary = "Consultar limite de categoria de um usuário", description = "Retorna o limite mensal definido para uma categoria de um usuário",
        responses = {
            @ApiResponse(responseCode = "200", description = "Limite retornado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCategoryLimitResponse.class))),
            @ApiResponse(responseCode = "404", description = "Limite não encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        }
    )
    @GetMapping("/{userId}/{categoryId}")
    public ResponseEntity<UserCategoryLimitResponse> getLimit(@PathVariable String userId,
                                                              @PathVariable String categoryId) {
        return ResponseEntity.ok(limitService.getLimit(userId, categoryId));
    }
}
