package refrigerator.back.member.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.dto.MemberDto;
import refrigerator.back.member.application.dto.MemberProfileImageDto;
import refrigerator.back.member.application.port.out.FindMyProfilePort;
import refrigerator.back.member.outbound.dto.OutMemberDto;
import refrigerator.back.member.outbound.mapper.OutMemberMapper;
import refrigerator.back.member.outbound.repository.query.MemberSelectQueryRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberDtoLookUpAdapter implements FindMyProfilePort {

    private final MemberSelectQueryRepository queryRepository;
    private final OutMemberMapper mapper;
    private final ImageUrlConvert imageUrlConvert;

    @Override
    public MemberDto getByEmail(String email) {
        OutMemberDto outMemberDto = queryRepository.selectMemberDtoByEmail(email);
        return outMemberDto.mapping(mapper, imageUrlConvert);
    }

    @Override
    public List<MemberProfileImageDto> getProfileImages() {
        List<MemberProfileImageDto> images = new ArrayList<>();
        MemberProfileImage[] values = MemberProfileImage.values();
        for (int imageNo = 0 ; imageNo < values.length ; imageNo++){
            images.add(new MemberProfileImageDto(imageNo,
                    imageUrlConvert.getUrl(values[imageNo].getName()))
            );
        }
        return images;
    }

}
