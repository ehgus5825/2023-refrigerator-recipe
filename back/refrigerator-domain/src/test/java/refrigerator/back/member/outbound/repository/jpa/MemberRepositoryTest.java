package refrigerator.back.member.outbound.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.member.application.domain.Member;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisabledRepositoryTest
@Import(QuerydslConfig.class)
@TestDataInit("/member.sql")
class MemberRepositoryTest {

    @Autowired MemberRepository repository;

    @Test
    @DisplayName("회원 조회 테스트")
    void findByEmail() {
        String email = "mstest102@gmail.com";
        Optional<Member> result = repository.findByEmail(email);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("같은 이메일을 가진 회원 수 조회")
    void countByEmail() {
        String email = "mstest102@gmail.com";
        int result = repository.countByEmail(email).intValue();
        assertEquals(1, result);
    }
}