package ao.com.wundu.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {

    private String path;
    private String method;
    private int status;
    private String statusText;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> erros;

    public ErrorMessage() {
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
        this(request, status, message);
        addErros(result);
    }

    private void addErros(BindingResult result) {
        this.erros = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            this.erros.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErros() {
        return erros;
    }
    public void setErros(Map<String, String> erros) {
        this.erros = erros;
    }
}
