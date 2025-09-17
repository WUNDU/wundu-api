package ao.com.wundu.ocr.dtos;

public record OcrResponse(
        String id,
        String userId,
        String fileName,
        String contentType,
        long fileSize,
        String text,
        String status
) {}
