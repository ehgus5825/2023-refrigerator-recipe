package refrigerator.back.mybookmark.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.mybookmark.application.domain.MyBookmark;
import refrigerator.back.mybookmark.application.port.in.AddMyBookmarkUseCase;
import refrigerator.back.mybookmark.application.port.in.DeleteMyBookmarkUseCase;
import refrigerator.back.mybookmark.application.port.out.FindMyBookmarkPort;
import refrigerator.back.mybookmark.application.port.out.SaveMyBookmarkPort;
import refrigerator.back.mybookmark.exception.MyBookmarkExceptionType;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class MyBookmarkService implements AddMyBookmarkUseCase, DeleteMyBookmarkUseCase {

    private final FindMyBookmarkPort findMyBookmarkPort;
    private final SaveMyBookmarkPort saveMyBookmarkPort;
    private final RecipeBookmarkModifyHandler modifyHandler;
    private final CurrentTime<LocalDateTime> currentTime;

    @Override
    public Long add(String memberId, Long recipeId) {
        MyBookmark myBookmark = findMyBookmarkPort.getMyBookmark(recipeId, memberId);
        if (myBookmark == null){
            MyBookmark newBookmark = MyBookmark.create(memberId, recipeId, currentTime.now(), modifyHandler);
            return saveMyBookmarkPort.save(newBookmark);
        }
        return myBookmark.add(modifyHandler);
    }

    @Override
    public Long delete(Long recipeId, String memberId) {
        MyBookmark myBookmark = findMyBookmarkPort.getMyBookmark(recipeId, memberId);
        if (myBookmark == null){
            throw new BusinessException(MyBookmarkExceptionType.NOT_FOUND_BOOKMARK);
        }
        return myBookmark.deleted(modifyHandler);
    }
}
