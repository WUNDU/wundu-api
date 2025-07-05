package ao.com.wundu.infra.persistence.dtos;

public record UserResponse(
       String id,
       String name,
       String email,
       String phoneNumber,
       String role,
       boolean isActive
) {
}
