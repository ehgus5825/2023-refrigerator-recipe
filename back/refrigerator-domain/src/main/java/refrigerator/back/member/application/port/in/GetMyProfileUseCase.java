package refrigerator.back.member.application.port.in;

import refrigerator.back.member.application.dto.MemberDto;
import refrigerator.back.member.application.dto.MemberProfileImageDto;

import java.util.List;

public interface GetMyProfileUseCase {
    MemberDto getMyProfile(String email);
    List<MemberProfileImageDto> getProfileImages();
}
