package ao.com.wundu.ocr.repository;

//import ao.com.wundu.ocr.entity.OcrRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcrRepository extends JpaRepository<OcrRecord, String> {}
