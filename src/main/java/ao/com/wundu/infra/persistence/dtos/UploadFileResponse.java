package ao.com.wundu.infra.persistence.dtos;

public record UploadFileResponse(
    String fileName,
    String fileType,
    long size,
    String downloadUri
) {}
