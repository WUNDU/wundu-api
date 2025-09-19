package ao.com.wundu.auditLogs.mapper;

import ao.com.wundu.auditLogs.dtos.AuditLogRequest;
import ao.com.wundu.auditLogs.dtos.AuditLogResponse;
import ao.com.wundu.auditLogs.entity.AuditLog;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class AuditLogMapper {

    public AuditLog toEntity(AuditLogRequest request) {
        return new AuditLog(
                request.actorId(),
                request.targetUserId(),
                request.action(),
                request.changedFields()
        );
    }

    public AuditLogResponse toResponse(AuditLog entity) {
        return new AuditLogResponse(
                entity.getId(),
                entity.getActorId(),
                entity.getTargetUserId(),
                entity.getAction(),
                entity.getChangedFields(),
                entity.getCreatedAt()
        );
    }

    public List<AuditLogResponse> toList(List<AuditLog> logs) {
        return logs.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
