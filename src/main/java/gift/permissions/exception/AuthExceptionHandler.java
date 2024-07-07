package gift.permissions.exception;

import gift.product.dto.ErrorResponseDto;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

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

    // 왜인지는 모르겠으나, UserEntity에서 반환한 제대로 예외가 발생하지 않고 자꾸 어딘가에서 처리하는 것 같습니다.
    // 그래서 ResponseStatusException에 대한 핸들러도 추가했습니다. error page를 띄우지 않아서 오히려 괜찮다는 생각은 들었습니다.
    @ExceptionHandler(ResponseStatusException.class)
    public ErrorResponseDto handler(ResponseStatusException responseStatusException) {
        String message = responseStatusException.getMessage();

        return new ErrorResponseDto(message);
    }
}
