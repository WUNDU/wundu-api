package ao.com.wundu.core.usecases.upload;

import ao.com.wundu.infra.persistence.dtos.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileUseCase {
    UploadFileResponse execute(MultipartFile file);
}
