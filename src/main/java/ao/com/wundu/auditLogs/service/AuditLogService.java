package ao.com.wundu.auditLogs.service;

import ao.com.wundu.auditLogs.dtos.AuditLogRequest;
import ao.com.wundu.auditLogs.dtos.AuditLogResponse;

import java.util.List;

public interface AuditLogService {

    AuditLogResponse create(AuditLogRequest request);

    AuditLogResponse findById(String id);

    List<AuditLogResponse> findByActor(String actorId);

    List<AuditLogResponse> findByTarget(String targetUserId);

    List<AuditLogResponse> findAll();
}
