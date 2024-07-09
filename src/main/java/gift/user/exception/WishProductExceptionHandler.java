package gift.user.exception;

import gift.global.dto.RestResponseDto;
import java.util.NoSuchElementException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// product package 예외 핸들러.
@RestControllerAdvice(basePackages = "gift.wish")
public class WishProductExceptionHandler {

    // IllegalArgument 핸들러 함수
    @ExceptionHandler(IllegalArgumentException.class)
    public RestResponseDto handler(IllegalArgumentException illegalArgumentException) {
        String message = illegalArgumentException.getMessage();

        return new RestResponseDto(message);
    }

    // NoSuchElement 핸들러 함수
    @ExceptionHandler(NoSuchElementException.class)
    public RestResponseDto handler(NoSuchElementException noSuchElementException) {
        String message = noSuchElementException.getMessage();

        return new RestResponseDto(message);
    }

    // MethodArgumentNotValid 핸들러 함수
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponseDto handler(MethodArgumentNotValidException methodArgumentNotValidException) {
        String message = methodArgumentNotValidException.getFieldError().getDefaultMessage();

        return new RestResponseDto(message);

    }
}
