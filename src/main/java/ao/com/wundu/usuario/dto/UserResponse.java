package ao.com.wundu.usuario.dto;

import ao.com.wundu.usuario.enums.PlanType;
import ao.com.wundu.usuario.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record UserResponse(
        String id,
        String name,
        String email,
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
