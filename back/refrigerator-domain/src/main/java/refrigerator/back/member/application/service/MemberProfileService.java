package refrigerator.back.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.member.application.dto.MemberDto;
import refrigerator.back.member.application.dto.MemberProfileImageDto;
import refrigerator.back.member.application.port.in.GetMyProfileUseCase;
import refrigerator.back.member.application.port.out.FindMyProfilePort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberProfileService implements GetMyProfileUseCase {

    private final FindMyProfilePort findMyProfilePort;

    @Override
    public MemberDto getMyProfile(String email) {
        return findMyProfilePort.getByEmail(email);
    }

    @Override
    public List<MemberProfileImageDto> getProfileImages() {
        return findMyProfilePort.getProfileImages();
    }
}
