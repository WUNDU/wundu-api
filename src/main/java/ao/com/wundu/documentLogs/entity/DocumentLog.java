package ao.com.wundu.documentLogs.entity;

import ao.com.wundu.documentLogs.enums.DocumentLogType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "document_logs")
public class DocumentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "actor_id", nullable = false)
    private String actorId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DocumentLogType type;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public DocumentLog() {}

    public DocumentLog(String actorId, String fileName, String contentType, Long fileSize, DocumentLogType type) {
        this.actorId = actorId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }

    public DocumentLog(String fileName, String contentType, Long fileSize, DocumentLogType type) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getActorId() {
        return actorId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public DocumentLogType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setType(DocumentLogType type) {
        this.type = type;
    }
}
