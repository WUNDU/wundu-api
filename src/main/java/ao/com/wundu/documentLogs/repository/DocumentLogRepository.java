package ao.com.wundu.documentLogs.repository;

import ao.com.wundu.documentLogs.entity.DocumentLog;
import ao.com.wundu.documentLogs.enums.DocumentLogType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentLogRepository extends JpaRepository<DocumentLog, String> {

    List<DocumentLog> findByActorId(String actorId);

    List<DocumentLog> findByType(DocumentLogType type);
}
