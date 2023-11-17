package refrigerator.back.myscore.application.domain;


import lombok.*;

import refrigerator.back.myscore.application.service.RecipeScoreModifyHandler;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipe_score_member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyScore extends ScoreScope{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Long scoreId;

    @Column(name = "member_email", nullable = false, length = 300)
    private String memberId;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Column(name = "score")
    private Double score;

    @Column(name = "create_date")
    private LocalDateTime createDateTime;

    private MyScore(String memberId, Long recipeId, Double score, LocalDateTime createDateTime) {
        checkScoreScope(score);
        this.memberId = memberId;
        this.recipeId = recipeId;
        this.score = score;
        this.createDateTime = createDateTime;
    }

    public void update(double newScore, RecipeScoreModifyHandler recipeScoreHandler){
        checkScoreScope(newScore);
        double oldScore = score;
        score = newScore;
        recipeScoreHandler.renew(recipeId, oldScore, newScore);
    }

    public static boolean isCooked(int number){
        return number == 1;
    }

    public static MyScore create(String memberId, Long recipeId,
                                 Double score, LocalDateTime createDateTime,
                                 RecipeScoreModifyHandler recipeScoreModifyHandler){
        MyScore myScore = new MyScore(memberId, recipeId, score, createDateTime);
        myScore.checkScoreScope(score);
        recipeScoreModifyHandler.addUp(recipeId, score);
        return myScore;
    }

    public static MyScore createForTest(String memberId, Long recipeId,
                                        Double score, LocalDateTime createDateTime){
        MyScore myScore = new MyScore(memberId, recipeId, score, createDateTime);
        myScore.checkScoreScope(score);
        return myScore;
    }
}
