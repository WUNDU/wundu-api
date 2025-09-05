package ao.com.wundu.usuario.dto;

import ao.com.wundu.usuario.enums.PlanType;

public record UserResponse(
       String id,
       String name,
       String email,
       String phoneNumber,
       String role,
       boolean isActive,
       String planType,
       boolean isTrial
) {
}
