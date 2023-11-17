package refrigerator.back.member.outbound.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.global.exception.MappingException;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.dto.MemberDto;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.back.member.outbound.dto.OutMemberDto;

import static org.junit.jupiter.api.Assertions.*;

class OutMemberMapperTest {

    private final OutMemberMapper mapper = Mappers.getMapper(OutMemberMapper.class);

    @Test
    @DisplayName("out -> in dto 변환 성공 테스트")
    void toMemberDto() {
        // given
        String nickname = "nickname";
        OutMemberDto outMemberDto = new OutMemberDto(nickname, "profileImage");
        String profileImageName = "profileImageName";
        // when
        MemberDto result = mapper.toMemberDto(outMemberDto, profileImageName);
        // then
        assertEquals(nickname, result.getNickname());
        assertEquals(profileImageName, result.getProfileImage());
    }

}