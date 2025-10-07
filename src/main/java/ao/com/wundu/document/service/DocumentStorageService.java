package ao.com.wundu.document.service;

import ao.com.wundu.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.UUID;

@Service
public class DocumentStorageService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentStorageService.class);

    // TODO: colocar o path para armazenar os file...
    @Value("${document.storage.path}")
    private String baseStoragePath;


    public String saveFile(MultipartFile file, String userId) {
        logger.info("Salvando arquivo {} para usuário {}", file.getOriginalFilename(), userId);

        try {
            Path userDirectory = createUserDirectory(userId);

            String sanitizedFileName = sanitizaFileName(file.getOriginalFilename());
            String uniqueFileName = generateUniqueFileName(sanitizedFileName);

            Path filePath = userDirectory.resolve(uniqueFileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            logger.info("Arquivo salvo: {}", filePath.toString());
            return uniqueFileName;

        } catch (IOException e) {

            // TODO: Verificar o tipo de Exception, ResourceNotFound não é a ideal
            logger.error("Erro ao salvar arquivo: {}", e.getMessage(), e);
            throw new ResourceNotFoundException("Falha ao salvar arquivo no storage",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String generateFileHash(MultipartFile file) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] filesBytes = file.getBytes();
            byte[] hashBytes = digest.digest(filesBytes);

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            logger.error("Erro ao gerar hash do arquivo: {}", e.getMessage());
            return UUID.randomUUID().toString(); // Fallback
        }
    }

    public byte[] readFile(String userId, String fileName) {

        try {
            Path filePath = Paths.get(baseStoragePath, userId, fileName);

            // TODO: Verificar o tipo de Exception, ResourceNotFound não é a ideal
            if ( !Files.exists(filePath) ) {
                throw new ResourceNotFoundException("Arquivo não encontrado", HttpStatus.NOT_FOUND);
            }

            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            // TODO: Verificar o tipo de Exception, ResourceNotFound não é a ideal
            logger.error("Erro ao ler arquivo: {}", e.getMessage());
            throw new ResourceNotFoundException("Erro ao ler arquivo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Path createUserDirectory(String userId) throws IOException {

        Path userDir = Paths.get(baseStoragePath, userId);

        //  TODO: Analisar melhor a exception "IOException" pode entrar em um try/catch ou permanecer como "throws IOException"
        if (!Files.exists(userDir)) {
            Files.createDirectories(userDir);
            logger.debug("Diretório criado para usuário: {}", userId);
        }

        return userDir;
    }

    private String sanitizaFileName(String originalName) {

        if (originalName == null) {
            return "unnamed_file";
        }

        String sanitized = originalName
                .replaceAll("[^a-zA-Z0-9._-]", "_")
                .replaceAll("\\.\\.+", ".")
                .replaceAll("^\\.", "")
                .replaceAll("\\.$", "");

        if (sanitized.length() > 100) {
            String extension = getFileExtension(sanitized);
            String nameWithoutExt = sanitized.substring(0, sanitized.lastIndexOf('.'));
            sanitized = nameWithoutExt.substring(0, 95) + "." + extension;
        }

        return sanitized.isEmpty() ? "unnamed_file" : sanitized;
    }

    private String generateUniqueFileName(String sanitizedName) {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String extension = getFileExtension(sanitizedName);
        String nameWithoutExt = sanitizedName.substring(0, sanitizedName.lastIndexOf('.'));

        return String.format("%s_%s.%s", nameWithoutExt, uniqueId, extension);
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex != -1) ? fileName.substring(lastDotIndex + 1) : "unknown";
    }
}
