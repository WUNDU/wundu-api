package ao.com.wundu.usuario.dto;

public record AuthResponse(
        String token,
        UserResponse user
) {
}