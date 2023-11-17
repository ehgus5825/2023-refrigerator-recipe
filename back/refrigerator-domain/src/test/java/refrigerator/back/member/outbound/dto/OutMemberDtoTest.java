package refrigerator.back.member.outbound.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.global.exception.MappingException;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.dto.MemberDto;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.back.member.outbound.mapper.OutMemberMapper;

import static org.junit.jupiter.api.Assertions.*;

class OutMemberDtoTest {

    OutMemberMapper mapper = Mappers.getMapper(OutMemberMapper.class);
    private final ImageUrlConvert imageUrlConvert = (parameter) -> "success";

    @Test
    @DisplayName("out -> in dto 변환 성공 테스트")
    void mappingTest() {
        // given
        String nickname = "nickname";
        MemberProfileImage profileImage = MemberProfileImage.PROFILE_IMAGE_FIVE;
        OutMemberDto outMemberDto = new OutMemberDto(nickname, profileImage.toString());
        // when
        MemberDto result = outMemberDto.mapping(mapper, imageUrlConvert);
        // then
        assertEquals(nickname, result.getNickname());
        assertEquals("success", result.getProfileImage());
    }

    @Test
    @DisplayName("out -> in dto 변환 실패 테스트 : 프로필 이미지가 잘못되었을 때")
    void mappingFailTest() {
        // given
        String nickname = "nickname";
        OutMemberDto outMemberDto = new OutMemberDto(nickname, "wrong");
        // when
        assertThrows(MappingException.class, () -> {
            try{
                outMemberDto.mapping(mapper, imageUrlConvert);
            } catch (MappingException e){
                assertInstanceOf(IllegalArgumentException.class, e.getCause());
                assertEquals(MemberExceptionType.NOT_FOUND_PROFILE_IMAGE, e.getBasicExceptionType());
                throw e;
            }
        });
    }
}