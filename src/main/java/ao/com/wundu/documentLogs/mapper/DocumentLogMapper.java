package ao.com.wundu.documentLogs.mapper;

import ao.com.wundu.documentLogs.dtos.DocumentLogRequest;
import ao.com.wundu.documentLogs.dtos.DocumentLogResponse;
import ao.com.wundu.documentLogs.entity.DocumentLog;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper entre entidades e DTOs de logs de documentos.
 */
@Component
public final class DocumentLogMapper {

    public DocumentLog toEntity(DocumentLogRequest request) {
        return new DocumentLog(
                request.actorId(),
                request.fileName(),
                request.contentType(),
                request.fileSize(),
                request.type()
        );
    }

    public DocumentLogResponse toResponse(DocumentLog entity) {
        return new DocumentLogResponse(
                entity.getId(),
                entity.getActorId(),
                entity.getFileName(),
                entity.getContentType(),
                entity.getFileSize(),
                entity.getType(),
                entity.getCreatedAt()
        );
    }

    public List<DocumentLogResponse> toList(List<DocumentLog> logs) {
        return logs.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
