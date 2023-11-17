package refrigerator.back.mybookmark.application.port.in;


import refrigerator.back.mybookmark.application.dto.InMyBookmarkPreviewsDto;

public interface FindMyBookmarkPreviewUseCase {
    InMyBookmarkPreviewsDto findPreviews(String memberId, int size);
}
