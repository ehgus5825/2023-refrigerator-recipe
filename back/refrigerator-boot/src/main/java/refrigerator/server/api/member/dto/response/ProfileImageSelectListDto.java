package refrigerator.server.api.member.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.member.application.dto.MemberProfileImageDto;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileImageSelectListDto {

    private List<MemberProfileImageDto> profileImages;

}
