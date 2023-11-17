package refrigerator.back.member.outbound.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.member.outbound.dto.OutMemberDto;

import static org.junit.jupiter.api.Assertions.*;

@DisabledRepositoryTest
@Import({QuerydslConfig.class, MemberSelectQueryRepository.class})
@TestDataInit("/member.sql")
class MemberLookUpQueryRepositoryTest {

    @Autowired
    MemberSelectQueryRepository queryRepository;

    @Test
    @DisplayName("회원 Dto 조회 쿼리 테스트")
    void selectMemberDtoByEmail() {
        String email = "mstest102@gmail.com";
        OutMemberDto result = queryRepository.selectMemberDtoByEmail(email);
        assertNotNull(result);
        assertNotEquals(OutMemberDto.builder().build(), result);
    }

    @Test
    @DisplayName("회원 Dto 조회 쿼리 실패 테스트")
    void selectMemberDtoByEmailFailTest() {
        String email = "wrong@gmail.com";
        OutMemberDto result = queryRepository.selectMemberDtoByEmail(email);
        assertNull(result);
    }

}