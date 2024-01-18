package refrigerator.back.mybookmark.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.global.exception.WriteQueryResultType;
import refrigerator.back.mybookmark.application.port.batch.DeleteBookmarkBatchPort;
import refrigerator.back.mybookmark.outbound.repository.query.MyBookmarkBatchQueryRepository;

@Component
@RequiredArgsConstructor
public class MyBookmarkBatchAdapter implements DeleteBookmarkBatchPort {

    private final MyBookmarkBatchQueryRepository myBookmarkBatchQueryRepository;


    @Override
    public WriteQueryResultType deleteMyBookmark() {
        return myBookmarkBatchQueryRepository.deleteMyBookmarkByDeletedStatus();
    }
}