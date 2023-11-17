package refrigerator.server.security.authentication.adapter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;

@Component
public class MemberInformationAdapter implements GetMemberEmailUseCase {

    public String getMemberEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
