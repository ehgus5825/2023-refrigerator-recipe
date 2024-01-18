package refrigerator.back.mybookmark.application.port.batch;

import refrigerator.back.global.exception.WriteQueryResultType;

public interface DeleteBookmarkBatchPort {

    WriteQueryResultType deleteMyBookmark();
}