package refrigerator.back.mybookmark.outbound.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.mybookmark.application.domain.MyBookmark;

import java.util.Optional;

public interface MyBookmarkJpaRepository extends JpaRepository<MyBookmark, Long> {
    MyBookmark findByRecipeIdAndMemberId(Long recipeId, String memberId);
}
