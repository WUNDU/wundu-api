package ao.com.wundu.notification.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationRequest() {
     @NotBlank(message = "O ID do usuário é obrigatório")
     String user_id,

     @NotBlank(message = "O title da notificação é obrigatóiro")
     String title,

     @NotBlank(message = "O id da categoria é obrigatório")
     String category_id
}
