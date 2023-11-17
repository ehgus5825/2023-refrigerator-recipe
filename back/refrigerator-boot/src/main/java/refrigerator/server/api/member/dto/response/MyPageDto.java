package refrigerator.server.api.member.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.member.application.dto.MemberDto;
import refrigerator.back.mybookmark.application.dto.InMyBookmarkPreviewsDto;
import refrigerator.back.myscore.application.dto.InMyScorePreviewsDto;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyPageDto {

    private MemberDto myProfile;
    private InMyScorePreviewsDto myScores;
    private InMyBookmarkPreviewsDto myBookmarks;

}
