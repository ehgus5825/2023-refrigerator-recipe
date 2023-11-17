package refrigerator.back.member.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.exception.MappingException;
import refrigerator.back.member.application.domain.MemberProfileImage;
import refrigerator.back.member.application.dto.MemberDto;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.back.member.outbound.dto.OutMemberDto;

@Mapper(componentModel = "spring")
public interface OutMemberMapper {

    OutMemberMapper INSTANCE = Mappers.getMapper(OutMemberMapper.class);

    MemberDto toMemberDto(OutMemberDto dto, String profileImage);


}
