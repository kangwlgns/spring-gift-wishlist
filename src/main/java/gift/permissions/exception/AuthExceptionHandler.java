package gift.permissions.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// user package 예외 핸들러
@RestControllerAdvice(basePackages = "gift.permissions")
public class AuthExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handler(MethodArgumentNotValidException methodArgumentNotValidException) {
        // 에러 메시지
        String message = methodArgumentNotValidException.getFieldError().getDefaultMessage();

        return message;
    }
}
