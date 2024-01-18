package refrigerator.server.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.back.member.application.dto.MemberProfileImageDto;
import refrigerator.back.mybookmark.application.dto.InMyBookmarkPreviewsDto;
import refrigerator.back.mybookmark.application.port.in.FindMyBookmarkPreviewUseCase;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.back.member.application.dto.MemberDto;
import refrigerator.back.member.application.port.in.GetMyProfileUseCase;
import refrigerator.back.myscore.application.dto.InMyScorePreviewsDto;
import refrigerator.back.myscore.application.port.in.FindMyScorePreviewsUseCase;
import refrigerator.server.api.member.dto.response.MyPageDto;

import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@Validated
public class MyPageController {

    private final GetMyProfileUseCase myProfileUseCase;
    private final FindMyScorePreviewsUseCase findMyScorePreviewsUseCase;
    private final FindMyBookmarkPreviewUseCase findMyBookmarkPreviewUseCase;
    private final GetMemberEmailUseCase getMemberEmailUseCase;

    @GetMapping("/api/my-page/member")
    public MyPageDto myPage(
            @RequestParam(value = "size", defaultValue = "5") @Positive Integer size){

        String email = getMemberEmailUseCase.getMemberEmail();
        MemberDto myProfile = myProfileUseCase.getMyProfile(email);
        InMyScorePreviewsDto myScores = findMyScorePreviewsUseCase.findMyScorePreviews(email, size);
        InMyBookmarkPreviewsDto myBookmarks = findMyBookmarkPreviewUseCase.findPreviews(email, size);
        return new MyPageDto(myProfile, myScores, myBookmarks);
    }

    @GetMapping("/api/my-page/member/profile/images")
    public BasicListResponseDTO<MemberProfileImageDto> getProfileImages(){

        return new BasicListResponseDTO<>(myProfileUseCase.getProfileImages());
    }
}
