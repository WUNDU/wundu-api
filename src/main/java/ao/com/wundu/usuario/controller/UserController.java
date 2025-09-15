package ao.com.wundu.usuario.controller;

import ao.com.wundu.usuario.dto.UserRequest;
import ao.com.wundu.usuario.dto.UserResponse;
import ao.com.wundu.exception.ErrorMessage;
import ao.com.wundu.usuario.entity.User;
import ao.com.wundu.usuario.enums.PlanType;
import ao.com.wundu.usuario.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import java.time.LocalDateTime;
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
    //@PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENTE') AND #id == authentication.principal.id)")
    //@PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('CLIENTE') and #id == principal.id)")
    public ResponseEntity<UserResponse> findById(@PathVariable String id) {
        UserResponse response = userService.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @Operation(
            summary = "Listar usuários por plano",
            description = "Retorna uma lista paginada de usuários filtrados pelo tipo de plano (FREE ou PREMIUM).",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Parâmetro inválido",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Não autenticado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (sem permissão)",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))

            }
    )
    @GetMapping("/plan")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponse> findByPlan(
            @RequestParam PlanType plan,
            Pageable pageable ) {
        return userService.findByPlanType(plan, pageable);
    }

    @Operation(
            summary = "Listar usuários por status de atividade",
            description = "Retorna uma lista paginada de usuários filtrados pelo campo isActive (true ou false).",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Parâmetro inválido",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Não autenticado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (sem permissão)",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))

            }
    )
    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponse> findByIsActive(
            @RequestParam Boolean isActive,
            Pageable pageable ) {
        return userService.findByIdIsActive(isActive, pageable);
    }

    @Operation(
            summary = "Listar usuários criados após uma data",
            description = "Retorna uma lista paginada de usuários filtrados pelo campo isActive (true ou false).",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Parâmetro inválido",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Não autenticado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (sem permissão)",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class)))

            }
    )
    @GetMapping("/created-after")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponse> findByCreatedAtAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAt,
            Pageable pageable) {
        return userService.findByCreatedAtAfter(createdAt, pageable);
    }
}
