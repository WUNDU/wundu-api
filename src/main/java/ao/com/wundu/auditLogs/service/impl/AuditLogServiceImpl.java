package ao.com.wundu.auditLogs.service.impl;

import ao.com.wundu.auditLogs.dtos.AuditLogRequest;
import ao.com.wundu.auditLogs.dtos.AuditLogResponse;
import ao.com.wundu.auditLogs.entity.AuditLog;
import ao.com.wundu.auditLogs.mapper.AuditLogMapper;
import ao.com.wundu.auditLogs.repository.AuditLogRepository;
import ao.com.wundu.auditLogs.service.AuditLogService;
import ao.com.wundu.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private AuditLogMapper mapper;

    @Override
    @Transactional
    public AuditLogResponse create(AuditLogRequest request) {
        AuditLog log = mapper.toEntity(request);
        log = auditLogRepository.save(log);
        return mapper.toResponse(log);
    }

    @Override
    public AuditLogResponse findById(String id) {
        AuditLog log = auditLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit log não encontrado com id=" + id));
        return mapper.toResponse(log);
    }

    @Override
    public List<AuditLogResponse> findByActor(String actorId) {
        List<AuditLog> logs = auditLogRepository.findByActorId(actorId);
        if (logs.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum audit log encontrado para actorId=" + actorId);
        }
        return mapper.toList(logs);
    }

    @Override
    public List<AuditLogResponse> findByTarget(String targetUserId) {
        List<AuditLog> logs = auditLogRepository.findByTargetUserId(targetUserId);
        if (logs.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum audit log encontrado para targetUserId=" + targetUserId);
        }
        return mapper.toList(logs);
    }

    @Override
    public List<AuditLogResponse> findAll() {
        return mapper.toList(auditLogRepository.findAll());
    }
}
