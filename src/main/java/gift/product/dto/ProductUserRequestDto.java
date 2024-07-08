package gift.product.dto;

// 유저로부터 입력 받는 dto. 이미 존재하는 제품에 대한 요청이므로 유효성 검증 x
public record ProductUserRequestDto(long id, String name, int price, String imageUrl) {

}
