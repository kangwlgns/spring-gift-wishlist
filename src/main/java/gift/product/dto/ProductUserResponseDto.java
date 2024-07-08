package gift.product.dto;

// 유저에게 보여즐 제품에 대한 dto. id는 보여주진 않지만 필요(구분자)
public record ProductUserResponseDto(long id, String name, int price, String imageUrl) {

}
