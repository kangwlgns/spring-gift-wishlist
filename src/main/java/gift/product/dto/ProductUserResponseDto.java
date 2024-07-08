package gift.product.dto;

// 유저에게 보여즐 제품에 대한 dto (id는 보여줄 필요가 없음)
public record ProductUserResponseDto(String name, int price, String imageUrl) {

}
