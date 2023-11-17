package refrigerator.back.member.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.application.port.out.UpdateMemberPort;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.back.member.outbound.repository.query.MemberUpdateQueryRepository;

@Component
@RequiredArgsConstructor
public class MemberUpdateAdapter implements UpdateMemberPort {

    private final MemberUpdateQueryRepository queryRepository;

    @Override
    public void updateToNickname(String email, String nickname) {
        queryRepository.updateNicknameByEmail(email, nickname)
                .throwExceptionWhenNotAllowDuplicationResource(MemberExceptionType.FAIL_UPDATE_MEMBER_NICKNAME);
    }

    @Override
    public void updateToPassword(String email, String password) {
        queryRepository.updatePasswordByEmail(email, password)
                .throwExceptionWhenNotAllowDuplicationResource(MemberExceptionType.FAIL_UPDATE_MEMBER_PASSWORD);
    }

    @Override
    public void updateToProfile(String email, MemberProfileImage profileImage) {
        queryRepository.updateProfileImageByEmail(email, profileImage)
                .throwExceptionWhenNotAllowDuplicationResource(MemberExceptionType.FAIL_UPDATE_MEMBER_PROFILE);
    }

    @Override
    public void updateToStatus(String email, MemberStatus status) {
        queryRepository.updateMemberStatusByEmail(email, status)
                .throwExceptionWhenNotAllowDuplicationResource(MemberExceptionType.FAIL_UPDATE_MEMBER_STATUS);
    }
}
