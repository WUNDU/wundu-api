package ao.com.wundu.ocr.dtos;

public record OcrResponse(String id, String fileName, String contentType, long fileSize, String text) {}
