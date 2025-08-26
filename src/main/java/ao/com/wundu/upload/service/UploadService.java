package ao.com.wundu.upload.service;

import ao.com.wundu.upload.dtos.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    UploadFileResponse uploadFile(MultipartFile file);

    byte[] loadFileAsBytes(String fileName);
}
