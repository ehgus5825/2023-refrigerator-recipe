package refrigerator.back.myscore.outbound.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.myscore.application.domain.MyScore;

import java.util.Optional;

public interface MyScoreJpaRepository extends JpaRepository<MyScore, Long> {
    Optional<MyScore> findByRecipeIdAndMemberId(Long recipeId, String memberId);
}
