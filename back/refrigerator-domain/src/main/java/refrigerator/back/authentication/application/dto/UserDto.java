package refrigerator.back.authentication.application.dto;


import lombok.Getter;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.exception.MemberExceptionType;

@Getter
public class UserDto {

    private final String username;
    private final String password;
    private final String status;

    public UserDto(String username, String password, String status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public boolean isEnable(){
        isNotWithdrawn();
        isNotDormant();
        isNotBlocked();
        return true;
    }

    private void isNotWithdrawn(){
        if (status.equals(MemberStatus.LEAVE_STATUS.toString())) {
            throw new BusinessException(MemberExceptionType.WITHDRAWN_MEMBER);
        }
    }

    private void isNotDormant(){
        if (status.equals(MemberStatus.DORMANT_STATUS.toString())) {
            throw new BusinessException(MemberExceptionType.DORMANT_MEMBER);
        }
    }

    private void isNotBlocked(){
        if (status.equals(MemberStatus.BLOCK_STATUS.toString())) {
            throw new BusinessException(MemberExceptionType.BLOCKED_MEMBER);
        }
    }

    public boolean isMember(){
        return status.equals(MemberStatus.STEADY_STATUS.toString());
    }

}
