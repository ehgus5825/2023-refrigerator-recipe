package refrigerator.back.mybookmark.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.mybookmark.application.dto.MyBookmarkDto;
import refrigerator.back.mybookmark.application.dto.InMyBookmarkPreviewsDto;
import refrigerator.back.mybookmark.application.dto.MyBookmarkPreviewDto;
import refrigerator.back.mybookmark.application.port.in.FindMyBookmarksUseCase;
import refrigerator.back.mybookmark.application.port.in.FindMyBookmarkPreviewUseCase;
import refrigerator.back.mybookmark.application.port.out.FindMyBookmarksPort;
import refrigerator.back.mybookmark.application.port.out.GetNumberOfMyBookmarkPort;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyBookmarkLookUpService implements FindMyBookmarkPreviewUseCase, FindMyBookmarksUseCase {

    private final FindMyBookmarksPort findBookmarkListPort;
    private final GetNumberOfMyBookmarkPort getNumberOfMyBookmarkPort;

    @Override
    public List<MyBookmarkDto> findBookmarks(String memberId, int page, int size) {
        return findBookmarkListPort.findBookmarks(memberId, page, size);
    }

    @Override
    public InMyBookmarkPreviewsDto findPreviews(String memberId, int size) {
        List<MyBookmarkPreviewDto> bookmarks = findBookmarkListPort.findBookmarkPreviews(memberId, size);
        Integer count = getNumberOfMyBookmarkPort.getByMemberId(memberId);
        return new InMyBookmarkPreviewsDto(bookmarks, count);
    }

}
