package ao.com.wundu.auditLogs.repository;

import ao.com.wundu.auditLogs.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, String> {

    List<AuditLog> findByActorId(String actorId);

    List<AuditLog> findByTargetUserId(String targetUserId);
}
