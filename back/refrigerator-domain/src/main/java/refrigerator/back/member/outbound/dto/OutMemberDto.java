package refrigerator.back.member.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import refrigerator.back.global.exception.MappingException;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.dto.MemberDto;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.back.member.outbound.mapper.OutMemberMapper;

@Getter
@EqualsAndHashCode
@Builder
public class OutMemberDto {

    private String nickname;
    private String profileImageName;

    @QueryProjection
    public OutMemberDto(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImageName = profileImage;
    }

    public MemberDto mapping(OutMemberMapper mapper, ImageUrlConvert imageUrlConvert){
        String profileImageName = getImageFileName(this.profileImageName);
        return mapper.toMemberDto(this, imageUrlConvert.getUrl(profileImageName));
    }

    private String getImageFileName(String profileImage){
        try{
            MemberProfileImage memberProfileImage = MemberProfileImage.valueOf(profileImage);
            return memberProfileImage.getName();
        }catch (IllegalArgumentException e){
            throw new MappingException(e, MemberExceptionType.NOT_FOUND_PROFILE_IMAGE);
        }
    }
}
