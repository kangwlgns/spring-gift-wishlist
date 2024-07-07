package gift.permissions.service;

import gift.permissions.component.TokenComponent;
import gift.user.dto.TokenDto;
import gift.permissions.dto.UserDto;
import gift.permissions.entity.UserEntity;
import gift.permissions.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// UserController로부터 입력을 받아서 엔터티를 사용해서 비즈니스 로직 수행
@Service
public class AuthService {
    private final UserDao userDao;
    private final TokenComponent tokenComponent;

    @Autowired
    public AuthService(UserDao userDao, TokenComponent tokenComponent) {
        this.userDao = userDao;
        this.tokenComponent = tokenComponent;
    }

    // 회원가입 비즈니스 로직 처리
    public TokenDto register(UserDto userDto) {
        UserEntity userEntity = userDao.insertUser(userDto);

        // 반환값은 주어진 정보를 토대로 생성한 토큰
        return getToken(userEntity.getEmail(), userEntity.getPassword());
    }

    // 로그인 비즈니스 로직 처리
    public TokenDto login(UserDto userDto) {
        UserEntity userEntity = userDao.selectUser(userDto.email());

        // 비밀번호 검증
        userEntity.verifyPassword(userDto.password());

        // 비밀번호 검증이 완료되면 토큰 발급
        return getToken(userEntity.getEmail(), userEntity.getPassword());
    }

    // 정보를 토대로 토큰을 반환. 유일하게 토큰을 생성하는 곳
    // 비즈니스 로직을 엔터티에 메서드로 만들고 서비스에서 해당 메서드를 호출하여 비즈니스 로직을 처리하고 싶었는데,
    // 엔터티가 스프링 빈이 아니라서 해당 메서드만 따로 서비스로 빼게 되었습니다.
    private TokenDto getToken(String email, String password) {
        return tokenComponent.getToken(email, password);
    }
}