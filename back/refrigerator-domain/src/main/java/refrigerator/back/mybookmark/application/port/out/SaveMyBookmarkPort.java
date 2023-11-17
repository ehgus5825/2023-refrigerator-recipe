package refrigerator.back.mybookmark.application.port.out;

import refrigerator.back.mybookmark.application.domain.MyBookmark;

public interface SaveMyBookmarkPort {
    Long save(MyBookmark myBookmark);
}