package refrigerator.back.annotation;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Usage :
 * 배포 자동화를 할 때, sql 더미 데이터로 테스트 데이터 초기화 작업이 필요한 테스트는 테스트 자동화 대상에서 제외하기 위해 해당 어노테이션을 사용
 * 통합 테스트와 중복된 검증이 존재하는 테스트를 제외하기 위해 해당 어노테이션 사용
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest
@Disabled
public @interface DisabledRepositoryTest {
}
