package refrigerator.server.security.common.email;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MemberInformationAdapter implements GetMemberEmailUseCase {

    public String getMemberEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
