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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
        try {
            DocumentLog log = mapper.toEntity(request);
            log = repository.save(log);
            return mapper.toResponse(log);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceNotFoundException("Violação de integridade ao salvar log", HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            throw new ResourceNotFoundException("Erro ao acessar o banco de dados", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public DocumentLogResponse findById(String id) {
        DocumentLog log = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Log não encontrado com id=" + id));
        return mapper.toResponse(log);
    }

    @Override
    public List<DocumentLogResponse> findByActor(String actorId) {
        List<DocumentLog> logs = repository.findByActorId(actorId);
        if (logs.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum log encontrado para actorId=" + actorId);
        }
        return mapper.toList(logs);
    }

    @Override
    public List<DocumentLogResponse> findByType(String type) {
        DocumentLogType logType;
        try 
        {
            logType = DocumentLogType.valueOf(type.toUpperCase());
        }
        catch (IllegalArgumentException e)
        {
            throw new ResourceNotFoundException(
                    "Tipo de log inválido: " + type,
                    HttpStatus.BAD_REQUEST
            );
        }

        List<DocumentLog> logs = repository.findByType(logType);
        if (logs.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum log encontrado para o tipo = " + type);
        }
        return mapper.toList(logs);
    }

    @Override
    public List<DocumentLogResponse> findAll() {
        List<DocumentLog> logs = repository.findAll();
        if (logs.isEmpty()){
            throw new ResourceNotFoundException("Nenhum log encontrado no sistema");
        }
        return mapper.toList(logs);
    }
}
