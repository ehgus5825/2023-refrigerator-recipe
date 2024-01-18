package refrigerator.server.api.mybookmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.mybookmark.application.dto.MyBookmarkDto;
import refrigerator.back.mybookmark.application.port.in.FindMyBookmarksUseCase;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class MyPageBookmarkDetailsController {

    private final FindMyBookmarksUseCase findMyBookmarksUseCase;
    private final GetMemberEmailUseCase getMemberEmailUseCase;

    @GetMapping("/api/my-page/bookmarks")
    public BasicListResponseDTO<MyBookmarkDto> findMyBookmarks(
             @RequestParam(value = "size", defaultValue = "11") @Positive Integer size,
             @RequestParam(value = "page", defaultValue = "0") @PositiveOrZero Integer page){

        String memberId = getMemberEmailUseCase.getMemberEmail();
        List<MyBookmarkDto> bookmarks = findMyBookmarksUseCase.findBookmarks(memberId, page, size);
        return new BasicListResponseDTO<>(bookmarks);
    }
}
