package ao.com.wundu.usuario.dto;

import java.time.LocalDateTime;

public record UserResponse(
//       String id,
//       String name,
//       String email,
//       String phoneNumber,
//       String role,
//       boolean isActive,
//       String planType,
//       boolean isTrial
        String id,
        String name,
        String email,
        //String password,
        String phoneNumber,
        String role,
        String planType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        //LocalDateTime lastLogin,
        //String createBy,
        //String modifiedBy;
        Boolean isActive,

        LocalDateTime planStart,

        LocalDateTime planEnd,
        Boolean isTrial
) {
}