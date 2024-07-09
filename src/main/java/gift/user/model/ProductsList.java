package gift.user.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// ArgumentResolvers를 위해 만든 어노테이션. model에 있는 것이 맞는지는 모르겠습니다.
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductsList {
}
