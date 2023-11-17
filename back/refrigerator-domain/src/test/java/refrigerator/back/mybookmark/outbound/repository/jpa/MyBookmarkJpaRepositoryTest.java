package refrigerator.back.mybookmark.outbound.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.mybookmark.application.domain.MyBookmark;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(QuerydslConfig.class)
class MyBookmarkJpaRepositoryTest {

    @Autowired MyBookmarkJpaRepository jpaRepository;

    @Test
    void findByRecipeIdAndMemberId() {
        String memberId = "memberId";
        Long recipeId = 1L;
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 11, 1, 16);
        MyBookmark myBookmark = MyBookmark.createForTest(memberId, recipeId, false, createDateTime);
        jpaRepository.save(myBookmark);
        MyBookmark result = jpaRepository.findByRecipeIdAndMemberId(recipeId, memberId);
        assertNotNull(result);
    }

    @Test
    void findByRecipeIdAndMemberIdFailTest() {
        String memberId = "memberId";
        Long recipeId = 1L;
        MyBookmark result = jpaRepository.findByRecipeIdAndMemberId(recipeId, memberId);
        assertNull(result);
    }
}