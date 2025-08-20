package ao.com.wundu.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public ResourceNotFoundException(String msg) {
        super(msg);
        this.status = HttpStatus.NOT_FOUND;
    }

    public ResourceNotFoundException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
