package refrigerator.back.mybookmark.application.port.out;

import refrigerator.back.mybookmark.application.domain.MyBookmark;

public interface FindMyBookmarkPort {
    MyBookmark getMyBookmark(Long recipeId, String memberId);
}
