package ao.com.wundu.notification.controller;

import ao.com.wundu.exception.ErrorMessage;
import ao.com.wundu.notification.dtos.NotificationResponse;
import ao.com.wundu.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Notifications", description = "Notificações automáticas sobre limites de gastos")
@RestController
@RequestMapping("api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService service) {
        this.notificationService = service;
    }

    @GetMapping("/unread/{userId}")
    @Operation(
        summary = "Listar notificações não lidas por usuário",
        description = "Retorna todas as notificações não lidas de um determinado usuário"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de notificações retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationResponse.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
        )
    })
    public ResponseEntity<List<NotificationResponse>> getUnreadByUser(@PathVariable String userId) {
        List<NotificationResponse> responses = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(responses);
    }
}
