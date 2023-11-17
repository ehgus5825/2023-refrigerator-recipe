package refrigerator.server.api.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicListResponseDTO<T> {

    @Valid
    @NotEmpty
    @Size(min = 1)
    List<T> data;
}
