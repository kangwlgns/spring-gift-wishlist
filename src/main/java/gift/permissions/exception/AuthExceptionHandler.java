package gift.permissions.exception;

import gift.product.dto.ErrorResponseDto;
import java.util.NoSuchElementException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// user package 예외 핸들러
@RestControllerAdvice(basePackages = "gift.permissions")
public class AuthExceptionHandler {

    // methodArgumentNotValid에 대한 핸들러. 메시지 반환
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto handler(MethodArgumentNotValidException methodArgumentNotValidException) {
        String message = methodArgumentNotValidException.getFieldError().getDefaultMessage();

        return new ErrorResponseDto(message);
    }

    // NoSuchElement에 대한 핸들러. 메시지 반환
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponseDto handler(NoSuchElementException noSuchElementException) {
        String message = noSuchElementException.getMessage();

        return new ErrorResponseDto(message);
    }

    // NoSuchElement에 대한 핸들러. 메시지 반환
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponseDto handler(IllegalArgumentException illegalArgumentException) {
        String message = illegalArgumentException.getMessage();

        return new ErrorResponseDto(message);
    }
}
