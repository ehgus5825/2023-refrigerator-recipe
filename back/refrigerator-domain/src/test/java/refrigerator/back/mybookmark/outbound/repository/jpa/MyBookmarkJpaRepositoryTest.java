package refrigerator.back.mybookmark.outbound.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.mybookmark.application.domain.MyBookmark;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(QuerydslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MyBookmarkJpaRepositoryTest {

    @Autowired MyBookmarkJpaRepository jpaRepository;

    @Autowired TestEntityManager em;

    @Test
    @DisplayName("북마크 조회 성공")
    void findByRecipeIdAndMemberId() {
        String memberId = "memberId";
        Long recipeId = 1L;
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 11, 1, 16);
        MyBookmark myBookmark = MyBookmark.createForTest(memberId, recipeId, false, createDateTime);
        em.persist(myBookmark);
        MyBookmark result = jpaRepository.findByRecipeIdAndMemberId(recipeId, memberId);
        assertNotNull(result);
    }

    @Test
    @DisplayName("북마크 조회 실패")
    void findByRecipeIdAndMemberIdFailTest() {
        String memberId = "memberId";
        Long recipeId = 1L;
        MyBookmark result = jpaRepository.findByRecipeIdAndMemberId(recipeId, memberId);
        assertNull(result);
    }
}