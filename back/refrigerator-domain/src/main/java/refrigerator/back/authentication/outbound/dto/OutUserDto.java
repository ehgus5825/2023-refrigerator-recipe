package refrigerator.back.authentication.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import refrigerator.back.authentication.application.dto.UserDto;

public class OutUserDto {

    private String username;
    private String password;
    private String status;

    @QueryProjection
    public OutUserDto(String username, String password, String status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public UserDto mapping(){
        return new UserDto(username, password, status);
    }

}
