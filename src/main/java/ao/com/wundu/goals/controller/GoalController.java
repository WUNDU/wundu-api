package ao.com.wundu.goals.controller;

import ao.com.wundu.exception.ErrorMessage;
import ao.com.wundu.goals.dtos.GoalProgressDTO;
import ao.com.wundu.goals.dtos.GoalRequestDTO;
import ao.com.wundu.goals.dtos.GoalResponseDTO;
import ao.com.wundu.goals.service.GoalService;
import ao.com.wundu.jwt.JwtUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Goals", description = "Contém todas as operações relativas às metas financeiras")
@RestController
@RequestMapping("api/v1/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    private JwtUserDetails getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (JwtUserDetails) auth.getPrincipal();
    }

    @PostMapping
    @Operation(summary = "Criar uma nova meta")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Meta criada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalResponseDTO.class))),
        @ApiResponse(responseCode = "422", description = "Dados inválidos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<GoalResponseDTO> create(@Valid @RequestBody GoalRequestDTO request) {
        JwtUserDetails user = getAuthenticatedUser();
        GoalResponseDTO response = goalService.create(user.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar meta existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Meta atualizada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Meta não encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<GoalResponseDTO> update(@PathVariable String id, @Valid @RequestBody GoalRequestDTO request) {
        JwtUserDetails user = getAuthenticatedUser();
        GoalResponseDTO response = goalService.update(id, user.getId(), request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar meta por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Meta encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Meta não encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<GoalResponseDTO> findById(@PathVariable String id) {
        JwtUserDetails user = getAuthenticatedUser();
        return ResponseEntity.ok(goalService.findById(id, user.getId()));
    }

    @GetMapping
    @Operation(summary = "Listar todas as metas do usuário autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalResponseDTO.class)))
    })
    public ResponseEntity<List<GoalResponseDTO>> findByUser() {
        JwtUserDetails user = getAuthenticatedUser();
        return ResponseEntity.ok(goalService.findByUser(user.getId()));
    }

    @PostMapping("/{id}/progress")
    @Operation(summary = "Adicionar progresso a uma meta")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Progresso adicionado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalProgressDTO.class))),
        @ApiResponse(responseCode = "404", description = "Meta não encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<GoalProgressDTO> addProgress(
            @PathVariable String id,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) LocalDate progressDate
    ) {
        JwtUserDetails user = getAuthenticatedUser();
        LocalDate date = progressDate == null ? LocalDate.now() : progressDate;
        GoalProgressDTO dto = goalService.addProgress(id, user.getId(), amount, date);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}/progress")
    @Operation(summary = "Listar histórico de progresso de uma meta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Histórico retornado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalProgressDTO.class))),
        @ApiResponse(responseCode = "404", description = "Meta não encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<List<GoalProgressDTO>> listProgress(@PathVariable String id) {
        JwtUserDetails user = getAuthenticatedUser();
        return ResponseEntity.ok(goalService.listProgress(id, user.getId()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover meta existente")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Meta removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Meta não encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        JwtUserDetails user = getAuthenticatedUser();
        goalService.delete(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
