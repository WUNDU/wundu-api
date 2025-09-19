package ao.com.wundu.documentLogs.service.impl;

import ao.com.wundu.documentLogs.dtos.DocumentLogRequest;
import ao.com.wundu.documentLogs.dtos.DocumentLogResponse;
import ao.com.wundu.documentLogs.entity.DocumentLog;
import ao.com.wundu.documentLogs.enums.DocumentLogType;
import ao.com.wundu.documentLogs.mapper.DocumentLogMapper;
import ao.com.wundu.documentLogs.repository.DocumentLogRepository;
import ao.com.wundu.documentLogs.service.DocumentLogService;
import ao.com.wundu.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentLogServiceImpl implements DocumentLogService {

    @Autowired
    private DocumentLogRepository repository;

    @Autowired
    private DocumentLogMapper mapper;

    @Override
    @Transactional
    public DocumentLogResponse create(DocumentLogRequest request) {
        DocumentLog log = mapper.toEntity(request);
        log = repository.save(log);
        return mapper.toResponse(log);
    }

    @Override
    public DocumentLogResponse findById(String id) {
        DocumentLog log = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Log não encontrado com id=" + id));
        return mapper.toResponse(log);
    }

    @Override
    public List<DocumentLogResponse> findByActor(String actorId) {
        return mapper.toList(repository.findByActorId(actorId));
    }

    @Override
    public List<DocumentLogResponse> findByType(String type) {
        DocumentLogType logType = DocumentLogType.valueOf(type.toUpperCase());
        return mapper.toList(repository.findByType(logType));
    }

    @Override
    public List<DocumentLogResponse> findAll() {
        return mapper.toList(repository.findAll());
    }
}
