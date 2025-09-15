package ao.com.wundu.exception;

import org.springframework.http.HttpStatus;

public class BusinessValidationException extends RuntimeException{

    private final HttpStatus status;

    public BusinessValidationException(String msg) {
        super(msg);
        this.status = HttpStatus.CONFLICT;
    }

    public BusinessValidationException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
