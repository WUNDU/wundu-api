package ao.com.wundu.document.service;

import ao.com.wundu.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
public class DocumentValidationService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentValidationService.class);


    // TODO: Colocar types files no arquivo aplication.**
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "application/pdf",
            "image/jpeg",
            "image/jpg",
            "image/png"
    );

    // TODO: Colocar tamanho do arquivo no arquivo aplication.**
    private static final long MAX_FILE_SIZE = 20 * 1024 * 1024; // 20MB em bytes

    public void validateFile(MultipartFile file) {
        logger.debug("Validando arquivo: {}", file.getOriginalFilename());

        validateBasicFile(file);

        validateContentType(file);

        validateFilesSize(file);

        logger.debug("Arquivo válido: {}", file.getOriginalFilename());
    }

    private void validateBasicFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            logger.warn("Tentativa de upload de arquivo vazio");
            // TODO: Verificar o tipo de Exception, ResourceNotFound não é a ideal
            throw new ResourceNotFoundException("Arquivo é obrigatório e não pode estar vazio",
                    HttpStatus.BAD_REQUEST);
        }

        if ( file.getOriginalFilename() == null || file.getOriginalFilename().trim().isEmpty() ) {
            logger.warn("Arquivo sem nome original");
            // TODO: Verificar o tipo de Exception, ResourceNotFound não é a ideal
            throw new ResourceNotFoundException("Nome do arquivo é obrigatório",
                    HttpStatus.BAD_REQUEST);
        }
    }

    private void validateContentType(MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType == null || ! ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            logger.warn("Tipo de arquivo não permitido: {}", contentType);
            // TODO: Verificar o tipo de Exception, ResourceNotFound não é a ideal
            throw new ResourceNotFoundException(
                    "Formato de arquivo não suportado. Apenas PDF, JPG, PNG são aceitos",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Validação forçada do tipos
        String fileName = file.getOriginalFilename().toLowerCase();
        if (!fileName.endsWith(".pdf") &&
                !fileName.endsWith(".jpg") &&
                !fileName.endsWith(".jpeg") &&
                !fileName.endsWith(".png")) {

            logger.warn("Extensão de arquivo inválida: {}", fileName);
            // TODO: Verificar o tipo de Exception, ResourceNotFound não é a ideal
            throw new ResourceNotFoundException(
                    "Extensão de arquivo não suportada. Use: .pdf, .jpg, .jpeg, .png",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    private void validateFilesSize(MultipartFile file) {
        long fileSize = file.getSize();

        if (fileSize > MAX_FILE_SIZE) {
            double fileSizeMB = fileSize / (1024.0 * 1024.0);
            logger.warn("Arquivo muito grande: {:.2f}MB", fileSizeMB);

            // TODO: Verificar o tipo de Exception, ResourceNotFound não é a ideal
            throw new ResourceNotFoundException(
                    String.format("Arquivo muito grande (%.2fMB). Tamanho máximo: 20MB", fileSizeMB),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (fileSize == 0) {
            logger.warn("Arquivo com tamanho zero");
            // TODO: Verificar o tipo de Exception, ResourceNotFound não é a ideal
            throw new ResourceNotFoundException("Arquivo não pode ter tamanho zero",
                    HttpStatus.BAD_REQUEST);
        }
    }

    public boolean isContentTypeSupported(String contentType) {
        return contentType != null && ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase());
    }
}
