package gift.user.controller;

import gift.global.dto.RestResponseDto;
import gift.permissions.component.TokenComponent;
import gift.user.dto.WishProductRequestDto;
import gift.user.dto.WishProductResponseDto;
import gift.user.service.WishProductService;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 개인의 wish list db를 조작해서 결과를 가져오는 api 컨트롤러
@RestController
@RequestMapping("/api/wishlist")
public class WishProductController {

    private final WishProductService wishProductService;
    private final TokenComponent tokenComponent;

    public WishProductController(WishProductService wishProductService,
        TokenComponent tokenComponent) {
        this.wishProductService = wishProductService;
        this.tokenComponent = tokenComponent;
    }

    // 전체 목록에서 제품 추가 시
    @PostMapping("/addition")
    public RestResponseDto addWishProduct(@RequestBody WishProductRequestDto wishProductRequestDto,
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        // 이 email도 담는 dto를 만드는 것이 나을까요?
        String email = tokenComponent.getEmail(token);

        wishProductService.insertWishProduct(email, wishProductRequestDto);
        return new RestResponseDto("추가가 완료되었습니다.");
    }

    // 한 유저의 위시 리스트 가져오기
    @GetMapping("/selection")
    public List<WishProductResponseDto> getWishProducts(
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String email = tokenComponent.getEmail(token);

        return wishProductService.readWishProducts(email);
    }

    // + 버튼 눌러서 하나 증가
    @PostMapping("/increase")
    public RestResponseDto increaseWishProduct(@RequestBody WishProductRequestDto wishProductRequestDto,
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String email = tokenComponent.getEmail(token);

        wishProductService.increaseWishProduct(email, wishProductRequestDto);
        return new RestResponseDto(RestResponseDto.SUCCESS);
    }

    // - 버튼 눌러서 하나 감소
    @PostMapping("/decrease")
    public RestResponseDto decreaseWishProduct(@RequestBody WishProductRequestDto wishProductRequestDto,
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String email = tokenComponent.getEmail(token);

        wishProductService.decreaseWishProduct(email, wishProductRequestDto);
        return new RestResponseDto(RestResponseDto.SUCCESS);
    }

    // 삭제 버튼 눌러서 삭제
    @PostMapping("/deletion")
    public RestResponseDto deleteWishProduct(@RequestBody WishProductRequestDto wishProductRequestDto,
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String email = tokenComponent.getEmail(token);

        wishProductService.deleteWishProduct(email, wishProductRequestDto);
        return new RestResponseDto(RestResponseDto.SUCCESS);
    }
}
