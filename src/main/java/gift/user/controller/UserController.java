package gift.user.controller;

import gift.permissions.component.TokenComponent;
import gift.product.dto.ProductUserResponseDto;
import gift.product.service.ProductService;
import gift.user.dto.WishProductResponseDto;
import gift.user.model.ProductsList;
import gift.user.service.WishProductService;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

// login한 user에게 보여줄 view를 반환하는 Controller
@Controller
@RequestMapping("/user")
public class UserController {

    private final WishProductService wishProductService;
    private final TokenComponent tokenComponent;

    public UserController(WishProductService wishProductService, TokenComponent tokenComponent) {
        this.wishProductService = wishProductService;
        this.tokenComponent = tokenComponent;
    }

    // 로그인 직후의 화면을 보여주는 핸들러
    @GetMapping("/main")
    public String userMain(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
        Model model) {
        // 특정 이메일을 갖는 사람이 추가한 위시 리스트 전체를 가져와서 thymeleaf를 통해 html로 전송
        String email = tokenComponent.getEmail(token);
        List<WishProductResponseDto> wishProductResponseDtoList = wishProductService.readWishProducts(email);

        model.addAttribute("products", wishProductResponseDtoList);
        model.addAttribute("token", token);
        return "html/user";
    }

    // 모든 제품을 추가하는 화면을 보여주는 핸들러
    @GetMapping("/all-products")
    public String showAllProducts(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
        @ProductsList List<ProductUserResponseDto> productsList, Model model) {
        // 모든 제품을 가져오는 행위는 resolver로 처리가 가능해서 resolver를 사용하였습니다.
        model.addAttribute("products", productsList);
        model.addAttribute("token", token);
        return "html/show-allproducts";
    }
}
