package gift.permissions.model;

import gift.user.dto.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.stereotype.Component;

// JWT 토큰을 생성해주는 bean 클래스.
// 슬랙의 글을 참고하여, util보다는 bean이 적합할 것 같아 bean으로 생성
@Component
public class TokenComponent {

    // 보안을 위해 token을 업데이트할 수 있도록 final로 선언하지 않기
    private final String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

    // 입력한 정보를 토대로 토큰을 반환하는 함수 (클래스끼리의 이동이므로 dto로 전달)
    public TokenDto getToken(String email, String password) {
        long currentTime = System.currentTimeMillis();
        String token = Jwts.builder()
            .subject(email.toString())
            .claim("password", password)
            .issuedAt(new Date(currentTime))
            // 유효기간은 60분
            .expiration(new Date(currentTime + minuteToMillis(60)))
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .compact();

        return new TokenDto(token);
    }

    // minute을 넣으면 밀리초로 반환하는 메서드
    private long minuteToMillis(int minute) {
        return minute * 60L * 1000;
    }
}
