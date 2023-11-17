package refrigerator.back.member.application.port.out;

import refrigerator.back.member.application.dto.MemberDto;
import refrigerator.back.member.application.dto.MemberProfileImageDto;

import java.util.List;

public interface FindMyProfilePort {
    MemberDto getByEmail(String email);
    List<MemberProfileImageDto> getProfileImages();
}
