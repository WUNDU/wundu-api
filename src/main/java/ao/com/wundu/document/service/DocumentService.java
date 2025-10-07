package ao.com.wundu.document.service;

import ao.com.wundu.document.dto.DocumentListDTO;
import ao.com.wundu.document.dto.DocumentStatusDTO;
import ao.com.wundu.document.dto.UploadResponseDTO;
import ao.com.wundu.document.entity.OcrRecord;
import ao.com.wundu.document.mapper.DocumentMapper;
import ao.com.wundu.document.repository.OcrRecordRepository;
import ao.com.wundu.exception.ResourceNotFoundException;
import ao.com.wundu.jwt.JwtUserDetails;
import ao.com.wundu.usuario.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DocumentService {

    // O.B.S: Estou a trabalhar com verificação de Role, para acessar os diferentes Endpoint aqui no serviço.
    // Podemos manter aqui ou colocar a validação no controller em cada metodo...

    private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);

    @Autowired
    private OcrRecordRepository ocrRecordRepository;

    @Autowired
    private DocumentValidationService validationService;

    @Autowired
    private DocumentStorageService storageService;

//    @Autowired
//    private AuditLogService auditLogService;

    @Autowired
    private DocumentMapper documentMapper;


    @Transactional
    public UploadResponseDTO uploadResponseDTO(MultipartFile file) {
        JwtUserDetails userDetails = getAuthenticatedUser();
        String userId = userDetails.getId();

        logger.info("Iniciando upload de documento para usuário: {}", userId);

        try {
            validationService.validateFile(file);

            if (ocrRecordRepository.existsByUserIdAndFileName(userId, file.getOriginalFilename())) {
                logger.warn("Arquivo duplicado detectado: {} para usuário: {}", file.getOriginalFilename(), userId);

                throw new ResourceNotFoundException("Arquivo com este nome já existe", HttpStatus.CONFLICT);
            }

            String savedFileName = storageService.saveFile(file, userId);

            OcrRecord ocrRecord = new OcrRecord(
                    userId,
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getSize()
            );

            ocrRecord = ocrRecordRepository.save(ocrRecord);

            // TODO: Gerar log de auditoria

            logger.info("Upload concluido com sucesso - ID: {}", ocrRecord.getId());

            return documentMapper.toUploadResponse(ocrRecord);

        } catch (ResourceNotFoundException e) {
            logger.error("Erro de validação no upload: {}", e.getMessage());
            // TODO: Aqui tem que vir audotoria de Erro
            throw e;

        } catch (Exception e) {
            logger.error("Erro inesperado no upload: {}", e.getMessage(), e);
            // TODO: Aqui tem que vir audotoria de Erro
            throw new ResourceNotFoundException("Falha interna no upload do documento",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<DocumentListDTO> listDocuments() {
        JwtUserDetails userDetails = getAuthenticatedUser();
        String userId = userDetails.getId();

        logger.debug("Listando documentos do usuário: {}", userId);

        List<OcrRecord> documents;

        if (userDetails.getRole().equals(Role.ADMIN.name())) {
            documents = ocrRecordRepository.findAll();
        } else {
            documents = ocrRecordRepository.findByUserIdOrderByCreatedAtDesc(userId);
        }

        return documentMapper.toDocumentListDTOs(documents);
    }

    public DocumentStatusDTO getDocumentStatus(String documentId) {
        JwtUserDetails userDetails = getAuthenticatedUser();
        String userId = userDetails.getId();

        logger.debug("Consultando status do documento: {} para usuário: {}", documentId, userId);

        OcrRecord document;

        if (userDetails.getRole().equals(Role.ADMIN.name())) {
            document = ocrRecordRepository.findById(documentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado"));
        } else {
            document = ocrRecordRepository.findByIdAndUserId(documentId, userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado"));
        }

        return documentMapper.documentStatusDTO(document);
    }

    public List<OcrRecord> getPendingDocuments() {
        return ocrRecordRepository.findPendingForOcr();
    }

    public long countUserDocuments(String userId) {
        return ocrRecordRepository.countByUserId(userId);
    }

    public List<DocumentListDTO> getDocumentsByStatus(String status) {
        JwtUserDetails userDetails = getAuthenticatedUser();

        List<OcrRecord> documents;
        if (userDetails.getRole().equals(Role.ADMIN.name())) {
            documents = ocrRecordRepository.findByStatus(status);
        } else {
            documents = ocrRecordRepository.findByUserIdAndStatus(userDetails.getId(), status);
        }

        return documentMapper.toDocumentListDTOs(documents);
    }

    private JwtUserDetails getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (JwtUserDetails) auth.getPrincipal();
    }
}
