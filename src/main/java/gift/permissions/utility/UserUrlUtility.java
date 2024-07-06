package gift.permissions.utility;

import java.util.List;

// Configurer에서 url을 필요로 하는데 UserController에 기능이 추가되면 개발자가 직접 추가해줘야 하므로 이를 간단하게 해보고자 만든 utility 클래스입니다.
public class UserUrlUtility {
    // 이렇게 만들어도 여전히 번거롭지만, configurer가 URL_LIST를 계속 가져가고 있으므로 그 과정을 줄인 것에 의의를 두었습니다.
    // 문득 만들고 보니, final 변수에 getter를 과연 사용하는 것이 옳은가? 라는 생각이 들게 되었습니다.
    public static final String MAIN = "/users/main";
    public static final List<String> URL_LIST = List.of(MAIN);
}
