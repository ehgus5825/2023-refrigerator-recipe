package refrigerator.back.member.application.port.out;

import refrigerator.back.member.application.domain.value.MemberProfileImage;
import refrigerator.back.member.application.domain.value.MemberStatus;

public interface UpdateMemberPort {

    void updateToNickname(String email, String nickname);
    void updateToPassword(String email, String password);
    void updateToProfile(String email, MemberProfileImage profileImage);
    void updateToStatus(String email, MemberStatus status);

}
