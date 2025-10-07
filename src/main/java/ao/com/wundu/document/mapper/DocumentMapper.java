package ao.com.wundu.document.mapper;

import ao.com.wundu.document.dto.DocumentListDTO;
import ao.com.wundu.document.dto.DocumentStatusDTO;
import ao.com.wundu.document.dto.UploadResponseDTO;
import ao.com.wundu.document.entity.OcrRecord;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DocumentMapper {

    public UploadResponseDTO toUploadResponse(OcrRecord ocrRecord) {
        return new UploadResponseDTO(
                ocrRecord.getId(),
                ocrRecord.getStatus().name(),
                "Upload Realizado com sucesso"
        );
    }

    public DocumentListDTO toDocumentListDTO(OcrRecord ocrRecord) {
        return new DocumentListDTO(
                ocrRecord.getId(),
                ocrRecord.getFileName(),
                ocrRecord.getContentType(),
                ocrRecord.getFileSize(),
                ocrRecord.getStatus().name(),
                ocrRecord.getCreatedAt()
        );
    }

    public DocumentStatusDTO documentStatusDTO(OcrRecord ocrRecord) {
        return new DocumentStatusDTO(
                ocrRecord.getId(),
                ocrRecord.getFileName(),
                ocrRecord.getStatus().name(),
                ocrRecord.getFileSize(),
                ocrRecord.getExtractedText(),
                ocrRecord.getCreatedAt()
        );
    }

    public List<DocumentListDTO> toDocumentListDTOs(List<OcrRecord> ocrRecords) {
        return ocrRecords.stream()
                .map(this::toDocumentListDTO)
                .collect(Collectors.toList());
    }
}
