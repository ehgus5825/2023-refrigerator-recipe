package refrigerator.back.mybookmark.application.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.mybookmark.application.service.RecipeBookmarkModifyHandler;
import refrigerator.back.mybookmark.exception.MyBookmarkExceptionType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipe_bookmark_member")
@NoArgsConstructor
public class MyBookmark{

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long bookmarkId;

    @Column(name = "member_email")
    private String memberId;

    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "deleted")
    @Getter
    private Boolean deleted;

    @Column(name = "create_date")
    private LocalDateTime createDateTime;

    private MyBookmark(String memberId, Long recipeId, Boolean deleted, LocalDateTime createDateTime) {
        this.memberId = memberId;
        this.recipeId = recipeId;
        this.deleted = deleted;
        this.createDateTime = createDateTime;
    }

    public static MyBookmark createForTest(String memberId, Long recipeId, Boolean deleted, LocalDateTime createDateTime){
        return new MyBookmark(memberId, recipeId, deleted, createDateTime);
    }

    public static MyBookmark create(String memberId, Long recipeId,
                                    LocalDateTime createDateTime,
                                    RecipeBookmarkModifyHandler handler){
        handler.added(recipeId);
        return new MyBookmark(memberId, recipeId, false, createDateTime);
    }

    public static boolean isBookmarked(int number){
        return number == 1;
    }

    public Long add(RecipeBookmarkModifyHandler handler){
        if (!deleted){
            throw new BusinessException(MyBookmarkExceptionType.ALREADY_ADD_BOOKMARK);
        }
        deleted = false;
        handler.added(recipeId);
        return bookmarkId;
    }

    public Long deleted(RecipeBookmarkModifyHandler handler){
        if (deleted){
            throw new BusinessException(MyBookmarkExceptionType.ALREADY_DELETE_BOOKMARK);
        }
        deleted = true;
        handler.deleted(recipeId);
        return bookmarkId;
    }

}
