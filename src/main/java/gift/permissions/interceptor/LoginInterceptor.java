package gift.permissions.interceptor;

import gift.permissions.component.TokenComponent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// HandlerInterceptor를 구현하여 요청을 가로채는 인터셉터 클래스
// Spring bean으로 등록해서, Configurer에 의존성을 주입할 수 있도록 한다.
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private final TokenComponent tokenComponent;

    @Autowired
    private LoginInterceptor(TokenComponent tokenComponent) {
        this.tokenComponent = tokenComponent;
    }

    // controller 호출 전에 가로채는 previous interceptor
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String[] token = request.getHeader("Authorization").split(" ");
        // token[0] == "Bearer". 인증 방식을 넣어줘야 한다고 들어서 이런 식으로 작성하였습니다.
        String tokenString = token[1];
        tokenComponent.validateToken(tokenString);

        return true;
    }

}
