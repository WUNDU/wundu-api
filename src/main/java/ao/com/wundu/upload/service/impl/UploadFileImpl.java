package ao.com.wundu.upload.service.impl;

import ao.com.wundu.upload.dtos.UploadFileResponse;
import ao.com.wundu.upload.service.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

@Service
public class UploadFileImpl implements UploadService {

    private static final String UPLOAD_DIR = "/home/eges-code/Downloads";

    private static final Set<String> ALLOWED_TYPES = Set.of(
        "application/pdf",
        "image/png",
        "image/jpeg"
    );

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("pdf", "png", "jpeg", "jpg");

    @Override
    public UploadFileResponse uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arquivo inválido ou ausente");
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de arquivo não suportado");
        }

        try {
<<<<<<< HEAD
            String originalName = Path.of(file.getOriginalFilename() == null ? "file" : file.getOriginalFilename()).getFileName().toString();
            String fileName = UUID.randomUUID().toString() + "_" + originalName;
=======
            String originalName = Path.of(file.getOriginalFilename() == null
                    ? "file"
                    : file.getOriginalFilename()).getFileName().toString();

            String fileName = UUID.randomUUID() + "_" + originalName;
>>>>>>> 98cd802 (:construction: feat(uploads -> leadFileAsBytes): Modelo de leitura para o ocr.)

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path dest = uploadPath.resolve(fileName);
            file.transferTo(dest.toFile());

            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString();

            return new UploadFileResponse(fileName, contentType, file.getSize(), downloadUri);

        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar o arquivo", e);
        }
    }

    @Override
    public byte[] loadFileAsBytes(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new ResourceNotFoundException("Nome do arquivo não informado", HttpStatus.BAD_REQUEST);
        }

        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);

        if (!Files.exists(filePath)) {
            throw new ResourceNotFoundException("Arquivo não encontrado: " + fileName, HttpStatus.NOT_FOUND);
        }

        String fileExtension = getFileExtension(fileName).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
            throw new ResourceNotFoundException("Tipo de arquivo não suportado: ." + fileExtension, HttpStatus.BAD_REQUEST);
        }

        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new ResourceNotFoundException("Erro ao ler o arquivo: " + fileName, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return (lastDot != -1) ? fileName.substring(lastDot + 1) : "";
    }
}
