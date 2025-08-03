package ao.com.wundu.usuario.controller;

import ao.com.wundu.usuario.dto.UserRequest;
import ao.com.wundu.usuario.dto.UserResponse;
import ao.com.wundu.exception.ErrorMessage;
import ao.com.wundu.usuario.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> responses = userService.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(responses);
    }
}
