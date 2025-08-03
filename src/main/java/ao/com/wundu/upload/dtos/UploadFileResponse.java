package ao.com.wundu.upload.dtos;

public record UploadFileResponse(
    String fileName,
    String fileType,
    long size,
    String downloadUri
) {}
