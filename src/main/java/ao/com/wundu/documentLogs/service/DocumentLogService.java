package ao.com.wundu.documentLogs.service;

import ao.com.wundu.documentLogs.dtos.DocumentLogRequest;
import ao.com.wundu.documentLogs.dtos.DocumentLogResponse;

import java.util.List;

public interface DocumentLogService {

    DocumentLogResponse create(DocumentLogRequest request);

    DocumentLogResponse findById(String id);

    List<DocumentLogResponse> findByActor(String actorId);

    List<DocumentLogResponse> findByType(String type);

    List<DocumentLogResponse> findAll();
}
