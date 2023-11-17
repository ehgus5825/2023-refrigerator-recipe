package refrigerator.back.comment.application.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment_heart")
@Getter
@NoArgsConstructor
public class CommentHeart {

    @Id
    @Column(name = "comment_id")
    Long commentId;

    @Column(name = "count")
    Integer count;

    @Column(name = "delete_status")
    Boolean deleteStatus;

    public CommentHeart(Long commentId) {
        this.commentId = commentId;
        this.count = 0;
        this.deleteStatus = false;
    }

    public CommentHeart(Long commentId, Integer count, Boolean deleteStatus) {
        this.commentId = commentId;
        this.count = count;
        this.deleteStatus = deleteStatus;
    }

    public static CommentHeart create(Long commentId){
        return new CommentHeart(commentId);
    }

    public static CommentHeart createForTest(Long commentId, Integer count, Boolean deleteStatus){
        return new CommentHeart(commentId, count, deleteStatus);
    }

}
