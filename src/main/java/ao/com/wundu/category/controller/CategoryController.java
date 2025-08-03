package ao.com.wundu.category.controller;

import ao.com.wundu.category.dto.CategoryRequest;
import ao.com.wundu.category.dto.CategoryResponse;
import ao.com.wundu.category.service.CategoryService;
import ao.com.wundu.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categories", description = "Operações relacionadas às categorias de transações")
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Criar nova categoria", description = "Usuario criar uma nova categoria",
        responses = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "422", description = "Dados inválidos",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        }
    )
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.create(request);
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
        List<CategoryResponse> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }
}
