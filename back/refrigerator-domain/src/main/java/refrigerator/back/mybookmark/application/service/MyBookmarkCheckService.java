package refrigerator.back.mybookmark.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.mybookmark.application.domain.MyBookmark;
import refrigerator.back.mybookmark.application.port.in.CheckBookmarkedUseCase;
import refrigerator.back.mybookmark.application.port.out.GetNumberOfMyBookmarkPort;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyBookmarkCheckService implements CheckBookmarkedUseCase {

    private final GetNumberOfMyBookmarkPort getNumberOfMyBookmarkPort;

    @Override
    public Boolean isBookmarked(Long recipeId, String memberId) {
        Integer number = getNumberOfMyBookmarkPort.getByRecipeIdAndMemberId(recipeId, memberId);
        return MyBookmark.isBookmarked(number);
    }
}
