package refrigerator.server.api.global.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class InputDataFormatCheck {

    @JsonIgnore
    public static final String NICKNAME_REGEX = "^[가-힣 ]{3,10}|[a-zA-Z]+$";

    @JsonIgnore
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_]+@[a-zA-Z]+\\.[a-zA-Z]+$";

    @JsonIgnore
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{4,16}$";
}
