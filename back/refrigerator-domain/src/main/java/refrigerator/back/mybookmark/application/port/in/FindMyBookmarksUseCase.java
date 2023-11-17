package refrigerator.back.mybookmark.application.port.in;


import refrigerator.back.mybookmark.application.dto.MyBookmarkDto;

import java.util.List;

public interface FindMyBookmarksUseCase {
    List<MyBookmarkDto> findBookmarks(String memberId, int page, int size);
}
