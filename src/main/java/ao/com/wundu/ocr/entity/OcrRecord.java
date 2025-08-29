package ao.com.wundu.ocr.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ocr_records")
public class OcrRecord {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Lob
    @Column(name = "extracted_text", columnDefinition = "TEXT")
    private String extractedText;

    @Column(name = "file_size", nullable = false)
    private long fileSize;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public OcrRecord() {}

    public OcrRecord(String fileName, String contentType, String extractedText, long fileSize) {
        this.id = UUID.randomUUID().toString();
        this.fileName = fileName;
        this.contentType = contentType;
        this.extractedText = extractedText;
        this.fileSize = fileSize;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getFileName() { return fileName; }
    public String getContentType() { return contentType; }
    public String getExtractedText() { return extractedText; }
    public long getFileSize() { return fileSize; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(String id) { this.id = id; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    public void setExtractedText(String extractedText) { this.extractedText = extractedText; }
    public void setFileSize(long fileSize) { this.fileSize = fileSize; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
