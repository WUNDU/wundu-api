package ao.com.wundu.notification.dtos;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

public record NotificationResponse(
    String id,
    String userId,
    String categoryId,
    String title,
    String message,
    Boolean isRead,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Timestamp da criação da notificação")
    LocalDateTime createdAt
) {}
