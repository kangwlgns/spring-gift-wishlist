package gift.user.dto;

// 위시리스트에 넣는 요청 시 사용할 dto. 이미 등록된 상품을 담으므로 유효성 검사는 하지 않습니다.
public record WishProductRequestDto(
    long id,
    String name,
    int price,
    String imageUrl
) {
}
