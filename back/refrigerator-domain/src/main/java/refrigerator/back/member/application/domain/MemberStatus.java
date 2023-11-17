package refrigerator.back.member.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;


import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MemberStatus {

    STEADY_STATUS("정상", "ROLE_STEADY_STATUS"),
    DORMANT_STATUS("휴면", "ROLE_DORMANT_STATUS"),
    BLOCK_STATUS("차단", "ROLE_BLOCK_STATUS"),
    LEAVE_STATUS("탈퇴", "ROLE_LEAVE_STATUS"),
    ADMIN("관리자", "ROLE_ADMIN")
    ;

    private String statusName;
    private String statusCode;

    public static MemberStatus lookup(String code){
        return Arrays.stream(MemberStatus.values())
                .filter(n -> n.getStatusCode().equals(code))
                .findAny()
                .orElseThrow(() -> new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER_STATUS));
    }

    public static boolean isValidByCode(String statusCode){
        return Arrays.stream(MemberStatus.values())
                .anyMatch(status -> status.toString().equals(statusCode));
    }

}
