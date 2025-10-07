package ao.com.wundu.document.enums;

public enum DocumentType {

    PDF("application/pdf"),
    JPEG("image/jpeg"),
    JPG("image/jpg"),
    PNG("image/png");

    private final String mimeType;

    DocumentType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() { return mimeType; }

    public static boolean isSupported(String contentType) {
        if (contentType == null) return false;

        for (DocumentType type : values()) {
            if (type.getMimeType().equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }
}
