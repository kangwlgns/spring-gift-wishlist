package gift.permissions.interceptor;

import gift.permissions.utility.UserUrlUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// WebMvcConfigurer를 구현하는 클래스.
@Configuration
public class LoginConfigurer implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    @Autowired
    public LoginConfigurer(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    // 인터셉터를 추가하는 메서드를 재정의하여 loginInterceptor를 등록하도록 함.
    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry
            // 인터셉터 추가
            .addInterceptor(loginInterceptor)
            // 아직은 하나 뿐이지만 순번을 정했습니다.
            .order(1)
            // 가능한 경로
            .addPathPatterns(UserUrlUtility.URL_LIST);
    }
}
