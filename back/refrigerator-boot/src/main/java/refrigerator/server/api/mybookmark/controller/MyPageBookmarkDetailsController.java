package refrigerator.server.api.mybookmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.mybookmark.application.dto.MyBookmarkDto;
import refrigerator.back.mybookmark.application.port.in.FindMyBookmarksUseCase;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.server.api.mybookmark.dto.InMyBookmarkDetailsListDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPageBookmarkDetailsController {

    private final FindMyBookmarksUseCase findMyBookmarksUseCase;
    private final GetMemberEmailUseCase getMemberEmailUseCase;

    @GetMapping("/api/my-page/bookmarks")
    public InMyBookmarkDetailsListDto findMyBookmarks(@RequestParam(value = "size", defaultValue = "11") int size,
                                                      @RequestParam("page") int page){
        String memberId = getMemberEmailUseCase.getMemberEmail();
        List<MyBookmarkDto> bookmarks = findMyBookmarksUseCase.findBookmarks(memberId, page, size);
        return new InMyBookmarkDetailsListDto(bookmarks);
    }
}
