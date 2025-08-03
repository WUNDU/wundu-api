package ao.com.wundu.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLogin(

        @NotBlank(message = "O campo e-mail é obrigatório")
        @Email(message = "Formato do e-mail está invalido", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
        String email,

        @NotBlank(message = "O campo e-mail é obrigatório")
        @Size(min = 6, max = 12, message = "Senha deve ter no mínimo 8 caracteres e no máximo 12")
        String password

) {
}
