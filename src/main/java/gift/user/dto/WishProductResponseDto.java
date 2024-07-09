package gift.user.dto;

// 위시리스트를 조작해서 나온 결과를 담는 dto
public record WishProductResponseDto(
    long id,
    String name,
    int price,
    String imageUrl,
    int quantity) {

}
