package gift.permissions.controller;

import gift.permissions.service.AuthService;
import gift.user.dto.TokenDto;
import gift.permissions.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 로그인 또는 회원가입을 통해 이메일과 비밀번호를 받아서 토큰을 반환해주는 컨트롤러
@RestController
@RequestMapping("/users")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 회원 가입에 대한 핸들러
    @PostMapping("/registration")
    public TokenDto register(@RequestBody @Valid UserDto userDto) {
        // service로부터 비즈니스 로직을 완수한 결과를 반환
        return authService.register(userDto);
    }

    // 로그인에 대한 핸들러
    @PostMapping("/login")
    public TokenDto login(@ModelAttribute @Valid UserDto userDto) {
        // service로부터 비즈니스 로직을 완수한 결과를 반환
        return authService.login(userDto);
    }
}