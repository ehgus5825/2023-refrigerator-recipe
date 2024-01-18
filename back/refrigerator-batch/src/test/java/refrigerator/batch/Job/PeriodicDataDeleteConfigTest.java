package refrigerator.batch.Job;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import refrigerator.back.comment.application.domain.entity.Comment;
import refrigerator.back.comment.application.domain.entity.CommentHeart;
import refrigerator.back.comment.application.domain.entity.CommentHeartPeople;
import refrigerator.back.comment.application.domain.entity.CommentRecord;
import refrigerator.back.comment.outbound.repository.redis.CommentHeartPeopleRedisRepository;
import refrigerator.back.global.common.RandomUUID;
import refrigerator.back.ingredient.application.domain.entity.Ingredient;
import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;
import refrigerator.back.mybookmark.application.domain.MyBookmark;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBatchTest
@SpringBootTest
@TestPropertySource(properties = "job.name=periodicDeleteScheduleBatch_Job")
@Slf4j
class PeriodicDataDeleteConfigTest extends BatchTestSupport {

    @Autowired
    CommentHeartPeopleRedisRepository commentHeartPeopleRedisRepository;

    @Test
    @DisplayName("데이터 주기적으로 삭제하는 테스트")
    void periodicDataDeleteConfigTest() throws Exception{

        // given
        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        LocalDate now2 = LocalDate.of(2023,1,1);

        String email = "email123@gmail.com";

        entityManager.getTransaction().begin();

        Ingredient ingredient = Ingredient.builder()
                .name("감자")
                .image(1)
                .email(email)
                .capacity(30.0)
                .capacityUnit("g")
                .deleted(true)
                .registrationDate(now2)
                .storageMethod(IngredientStorageType.FRIDGE)
                .expirationDate(now2.plusDays(1))
                .build();

        MyBookmark bookmark = MyBookmark
                .createForTest(email, 1L, true, now);

        Comment comment = Comment.builder()
                .recipeId(1L)
                .writerId(email)
                .commentRecord(new CommentRecord(now, true, false))
                .content("aaaaaa")
                .build();

        save(ingredient);
        save(bookmark);
        save(comment);

        CommentHeart commentHeart = new CommentHeart(comment.getCommentId(), 0, true);

        save(commentHeart);

        RandomUUID uuid = () -> "12345678";
        CommentHeartPeople commentHeartPeople = new CommentHeartPeople(
                uuid.getUUID().substring(0, 8), comment.getCommentId(), email);

        commentHeartPeopleRedisRepository.save(commentHeartPeople);

        entityManager.getTransaction().commit();

        // when
        JobParameters jobParameters = getUniqueParameterBuilder()
                .toJobParameters();

        JobExecution jobExecution = launchJob(jobParameters);

        //then
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        entityManager.getTransaction().begin();

        entityManager.flush();
        entityManager.clear();

        assertThat(entityManager.find(Ingredient.class, ingredient.getId()))
                .isEqualTo(null);

        assertThat(entityManager.find(MyBookmark.class, bookmark.getBookmarkId()))
                .isEqualTo(null);

        assertThat(entityManager.find(Comment.class, comment.getCommentId()))
                .isEqualTo(null);

        assertThat(entityManager.find(CommentHeart.class, commentHeart.getCommentId()))
                .isEqualTo(null);

        assertThat(commentHeartPeopleRedisRepository.findById(commentHeartPeople.getId()))
                .isEqualTo(Optional.empty());

        entityManager.getTransaction().commit();
    }
}