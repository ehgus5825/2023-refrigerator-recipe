package refrigerator.back.mybookmark.application.port.out;


import refrigerator.back.mybookmark.application.dto.MyBookmarkDto;
import refrigerator.back.mybookmark.application.dto.MyBookmarkPreviewDto;

import java.util.List;

public interface FindMyBookmarksPort {
    List<MyBookmarkDto> findBookmarks(String memberId, int page, int size);
    List<MyBookmarkPreviewDto> findBookmarkPreviews(String memberId, int size);
}
