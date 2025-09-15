package ao.com.wundu.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(
            AccessDeniedException ex,
            HttpServletRequest request,
            BindingResult result) {

        logger.error("API ERROR - ", ex);

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, "Campo(a) invalido(a)", result));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request,
            BindingResult result) {

        List<String> errors = result.getFieldErrors().stream()
                .map(fieldError -> String.format(
                        "%sCampo%s '%s': %s%s%s (valor rejeitado: %s'%s'%s)",
                        RED, RESET,
                        fieldError.getField(),
                        YELLOW, fieldError.getDefaultMessage(), RESET,
                        RED, fieldError.getRejectedValue(), RESET
                ))
                .toList();

        logger.error("\n\n🔴 API ERROR - VALIDAÇÃO FALHOU:\n{}", String.join("\n", errors));

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(a) invalido(a)", result));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceAccessException(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

      /*  logger.error(
                "\n{}🔴 API ERROR{}\n\n{}Mensagem:{} {}{}\n{}Origem:{} {}{}\n{}Stacktrace:{}\n{}\n",
                RED, RESET,
                BLUE, RESET, YELLOW + ex.getMessage() + RESET,
                BLUE, RESET, YELLOW + ex.getClass().getName() + RESET,
                BLUE, RESET
        );*/

        return ResponseEntity
                .status(ex.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, ex.getStatus(), ex.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessage> resourceAccessException( BusinessException ex,  HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        /*logger.error(
                "\n{}🔴 API ERROR{}\n\n{}Mensagem:{} {}{}\n{}Origem:{} {}{}\n{}Stacktrace:{}\n{}\n",
                RED, RESET,
                BLUE, RESET, YELLOW + ex.getMessage() + RESET,
                BLUE, RESET, YELLOW + ex.getClass().getName() + RESET,
                BLUE, RESET
        );*/

        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, status, ex.getMessage()));
    }

}
