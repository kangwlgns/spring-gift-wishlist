package gift.user.service;

import gift.user.dto.WishProductRequestDto;
import gift.user.dto.WishProductResponseDto;
import gift.user.repository.WishProductDao;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishProductService {
    private final WishProductDao wishProductDao;

    public WishProductService(WishProductDao wishProductDao) {
        this.wishProductDao = wishProductDao;
    }

    // 추가
    public void insertWishProduct(String email, WishProductRequestDto wishProductRequestDto) {
        // insertWishProduct의 반환값이 이미 key를 가진 로우가 있어서 삽입에 실패했는지를 나타내는데, 메서드 이름에서 유추할 수 없어서 괜찮은 방법인지는 모르겠습니다.
        // 하지만 코드 재사용성이 가장 높은 방법이라고 생각해서 이와 같은 방식을 사용했습니다.
        boolean insertSuccess = wishProductDao.insertWishProduct(email, wishProductRequestDto);

        // 만약 이미 있는 요소에 insert 요청을 넣었다면
        if (!insertSuccess) {
            // 위시 리스트의 제품 개수 하나 늘리기
            increaseWishProduct(email, wishProductRequestDto);
        }
    }

    // 읽기
    public List<WishProductResponseDto> readWishProducts(String email) {
        return wishProductDao.selectWishProducts(email);
    }

    // 개수 증가
    public void increaseWishProduct(String email, WishProductRequestDto wishProductRequestDto) {
        long id = wishProductRequestDto.id();
        WishProductResponseDto wishProductResponseDto = wishProductDao.selectWishProduct(email, id);

        // 하나 더해서 update 요청
        int quantity = wishProductResponseDto.quantity() + 1;
        wishProductDao.updateWishProductQuantity(email, id, quantity);
    }

    // 개수 감소
    public void decreaseWishProduct(String email, WishProductRequestDto wishProductRequestDto) {
        long id = wishProductRequestDto.id();
        WishProductResponseDto wishProductResponseDto = wishProductDao.selectWishProduct(email, id);

        // 하나 빼서 update 요청
        int quantity = wishProductResponseDto.quantity() - 1;
        wishProductDao.updateWishProductQuantity(email, id, quantity);
    }

    // 삭제
    public void deleteWishProduct(String email, WishProductRequestDto wishProductRequestDto) {
        wishProductDao.deleteWishProduct(email, wishProductRequestDto.id());
    }
}
