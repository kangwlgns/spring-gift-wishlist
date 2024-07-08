package gift.permissions.component;

import gift.product.dto.ProductUserResponseDto;
import gift.product.service.ProductService;
import gift.user.model.ProductsList;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// 인증 방식 + 토큰 형태의 입력을 토큰으로 반환하는 resolver
@Component
public class UserAllProductsResolver implements HandlerMethodArgumentResolver {
    private final ProductService productService;

    @Autowired
    public UserAllProductsResolver(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 어노테이션이 붙었는지 확인
        boolean isTokenAnnotation = parameter.hasParameterAnnotation(ProductsList.class);
        // 리스트 여부 확인
        boolean isList = parameter.getParameterType().equals(List.class);
        // 리스트의 제네릭 확인
        ParameterizedType parameterizedType = (ParameterizedType) parameter.getGenericParameterType();
        boolean isProductList = parameterizedType.getActualTypeArguments()[0].equals(ProductUserResponseDto.class);

        return isTokenAnnotation && isList && isProductList;
    }

    // 모든 제품 목록 가져 옴.
    @Override
    public List<ProductUserResponseDto> resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        List<ProductUserResponseDto> products = productService.selectProductsForUser();

        return products;
    }
}
