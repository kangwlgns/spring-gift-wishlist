package gift.product.controller;

import gift.product.dto.ProductUserResponseDto;
import gift.product.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// step3를 진행하기 전 전체 리팩토링을 진행하면 시간이 너무 오래 걸리게 될 것 같아서 필요한 일부만 리팩토링한 컨트롤러입니다.
@RestController
@RequestMapping("/api/products")
public class RefactoringProductController {
    private final ProductService productService;

    @Autowired
    public RefactoringProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/user/selection")
    public List<ProductUserResponseDto> getProductsForUser() {
        List<ProductUserResponseDto> products = productService.selectProductsForUser();

        return products;
    }
}
