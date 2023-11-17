package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.port.in.ModifyMemberUseCase;
import refrigerator.back.member.application.port.out.UpdateMemberPort;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberModifyService implements ModifyMemberUseCase {

    private final UpdateMemberPort updateMemberPort;

    @Override
    public void modifyNickname(String email, String nickname) {
        updateMemberPort.updateToNickname(email, nickname);
    }

    @Override
    public void modifyPassword(String email, String password) {
        updateMemberPort.updateToPassword(email, password);
    }

    @Override
    public void modifyProfileImage(String email, Integer imageNo) {
        updateMemberPort.updateToProfile(email, MemberProfileImage.pickUp(imageNo));
    }
}
