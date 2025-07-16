package ao.com.wundu.infra.persistence.controller;

import ao.com.wundu.core.usecases.category.CreateCategoryUseCase;
import ao.com.wundu.core.usecases.category.ListAllCategoriesUseCase;
import ao.com.wundu.infra.persistence.dtos.CategoryRequest;
import ao.com.wundu.infra.persistence.dtos.CategoryResponse;
import ao.com.wundu.infra.persistence.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categorias", description = "Operações relacionadas às categorias de transações")
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private ListAllCategoriesUseCase listAllCategoriesUseCase;

    @Operation(summary = "Criar nova categoria", description = "Apenas ADMIN pode criar uma nova categoria",
        responses = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "422", description = "Dados inválidos",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        }
    )
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse response = createCategoryUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todas as categorias", description = "Acesso ADMIN ou CLIENTE",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class)))
        }
    )
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        List<CategoryResponse> categories = listAllCategoriesUseCase.execute();
        return ResponseEntity.ok(categories);
    }
}
