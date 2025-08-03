package ao.com.wundu.usuario.dto;

public record UserResponse(
       String id,
       String name,
       String email,
       String phoneNumber,
       String role,
       boolean isActive
) {
}
