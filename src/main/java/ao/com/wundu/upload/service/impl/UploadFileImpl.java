package ao.com.wundu.upload.service.impl;

import ao.com.wundu.upload.dtos.UploadFileResponse;
import ao.com.wundu.upload.service.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadFileImpl implements UploadService {

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public UploadFileResponse uploadFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists())
                uploadDir.mkdirs();

            File dest = new File(UPLOAD_DIR + fileName);
            file.transferTo(dest);

            return new UploadFileResponse(
                    fileName,
                    file.getContentType(),
                    file.getSize(),
                    "/uploads/" + fileName);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar o arquivo", e);
        }
    }
}
