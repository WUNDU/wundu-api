package ao.com.wundu.usuario.controller;

import ao.com.wundu.usuario.dto.UserRequest;
import ao.com.wundu.usuario.dto.UserResponse;
import ao.com.wundu.exception.ErrorMessage;
import ao.com.wundu.usuario.enums.PlanType;
import ao.com.wundu.usuario.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Tag(name = "WUNDU", description = "Contém todas as operações relativas aos recursos para cadastro, edição, e leitura de um usuário")
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Criar umm novo usuário", description = "Recurso para criar um novo usuário",
            responses = {
                @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidas",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {

        UserResponse response = userService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "Recuperar um usuário por id", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN|CLIENTE",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENTE') AND #id == authentication.principal.id)")
    public ResponseEntity<UserResponse> findById(@PathVariable String id) {
        UserResponse response = userService.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @Operation(summary = "Listar todos os usuários", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN|CLIENTE",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de todos usuários encontrados com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)))
            }
    )
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<Page<UserResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) PlanType plan,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Timestamp createdAt) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserResponse> responses = userService.findAll(plan, isActive, createdAt, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(responses);
    }
}
