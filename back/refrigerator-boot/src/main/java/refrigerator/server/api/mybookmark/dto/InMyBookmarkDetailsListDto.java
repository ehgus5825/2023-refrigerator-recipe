package refrigerator.server.api.mybookmark.dto;

import lombok.*;
import refrigerator.back.mybookmark.application.dto.MyBookmarkDto;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InMyBookmarkDetailsListDto {
    private List<MyBookmarkDto> bookmarks;
}
