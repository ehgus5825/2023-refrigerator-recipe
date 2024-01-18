package refrigerator.back.member.outbound.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refrigerator.back.member.application.dto.MemberDto;
import refrigerator.back.member.outbound.dto.OutMemberDto;

@Mapper(componentModel = "spring")
public interface OutMemberMapper {

    OutMemberMapper INSTANCE = Mappers.getMapper(OutMemberMapper.class);

    MemberDto toMemberDto(OutMemberDto dto, String profileImage);

}
