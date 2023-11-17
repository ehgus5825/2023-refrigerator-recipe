package refrigerator.back.comment.application.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "recipe_comment")
@Getter
@NoArgsConstructor
@Slf4j
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_comment_id")
    private Long commentId;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Column(name = "member_email", nullable = false)
    private String writerId;

    @Column(name = "content", nullable = false)
    private String content;

    @Embedded
    private CommentRecord commentRecord;

    private Comment(Long recipeId, String writerId, String content, LocalDateTime createDateTime) {
        this.recipeId = recipeId;
        this.content = content;
        this.writerId = writerId;
        this.commentRecord = new CommentRecord(createDateTime);
    }

    @Builder
    private Comment(Long commentId, Long recipeId, String writerId, String content, CommentRecord commentRecord) {
        this.commentId = commentId;
        this.recipeId = recipeId;
        this.writerId = writerId;
        this.content = content;
        this.commentRecord = commentRecord;
    }

    public static Comment write(Long recipeId, String writerId, String content, LocalDateTime createDateTime){
        return new Comment(recipeId, writerId, content, createDateTime);
    }

    public static Comment createForTest(Long commentId, Long recipeId,
                                        String writerId, String content,
                                        LocalDateTime createDateTime){
        return Comment.builder()
                .commentId(commentId)
                .recipeId(recipeId)
                .writerId(writerId)
                .content(content)
                .commentRecord(new CommentRecord(createDateTime)).build();
    }

}
