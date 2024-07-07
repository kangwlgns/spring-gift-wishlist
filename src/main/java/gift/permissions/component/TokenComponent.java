package gift.permissions.component;

import gift.user.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

// JWT 토큰을 생성해주는 bean 클래스.
// 슬랙의 글을 참고하여, util보다는 bean이 적합할 것 같아 bean으로 생성
@Component
public class TokenComponent {

    // 보안을 위해 token을 업데이트할 수 있도록 final로 선언하지 않기
    private static final String SECRET_KEY_STRING = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
    private final Key secretKey;

    // 기본 생성자를 통해 미리 특정 알고리즘으로 인코딩한 키를 생성
    public TokenComponent() {
        secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
    }

    // 입력한 정보를 토대로 토큰을 반환하는 함수 (클래스끼리의 이동이므로 dto로 전달)
    public TokenDto getToken(String email, String password) {
        long currentTime = System.currentTimeMillis();
        String token = Jwts.builder()
            .subject(email.toString())
            .claim("password", password)
            .issuedAt(new Date(currentTime))
            // 유효기간은 60분
            .expiration(new Date(currentTime + minuteToMillis(60)))
            .signWith(secretKey)
            .compact();

        return new TokenDto(token);
    }

    // secretKey를 기반으로 토큰 디코딩하는 함수
    public void validateToken(String token) {
        try {
            // deprecated라고 해서 parserBuilder()를 사용하려고 하니 찾을 수가 없었습니다.
            // gradle에서 주입해줘야 하는 것 같은데, 수정하면 안 될 것 같아서 일단 parser()를 사용했습니다.
            Jws<Claims> claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            // 서명이 잘못됐거나, 토큰이 잘못됐거나, 유효기간이 지났거나 등등의 이유로 다양한 예외가 발생할 수 있으므로 Exception으로 잡은 후에 401을 반환하겠습니다.
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "다시 로그인 필요");
        }
    }

    // minute을 넣으면 밀리초로 반환하는 메서드
    private long minuteToMillis(int minute) {
        return minute * 60L * 1000;
    }
}