package refrigerator.back.member.adapter.in.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberBasicDTO<T>{
    private T data;
}
