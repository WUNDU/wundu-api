package ao.com.wundu.document.repository;

import ao.com.wundu.document.entity.OcrRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OcrRecordRepository extends JpaRepository<OcrRecord, String> {

    List<OcrRecord> findByUserIdOrderByCreatedAtDesc(String userId);

    Optional<OcrRecord> findByIdAndUserId(String id, String userId);

    List<OcrRecord> findByStatus(String status);

    List<OcrRecord> findByUserIdAndStatus(String userId, String status);

    long countByUserId(String userId);

    boolean existsByUserIdAndFileName(String userId, String fileName);

    @Query("SELECT o FROM OcrRecord o WHERE o.status = 'pending' ORDER BY o.createdAt ASC")
    List<OcrRecord> findPendingForOcr();
}
