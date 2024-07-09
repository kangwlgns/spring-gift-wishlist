package gift.global.dto;

public record RestResponseDto(String response) {
    // 반환할 필요는 없지만 성공했음을 나타내는 문자열 success
    public final static String SUCCESS = "success";
}
