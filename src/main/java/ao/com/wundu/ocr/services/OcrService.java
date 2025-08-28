package ao.com.wundu.ocr.service;

import ao.com.wundu.ocr.dto.OcrResponse;
import org.springframework.web.multipart.MultipartFile;

public interface OcrService {
    OcrResponse processOcr(MultipartFile file);
}
