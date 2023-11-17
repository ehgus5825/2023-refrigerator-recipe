package refrigerator.back.mybookmark.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.mybookmark.application.dto.MyBookmarkDto;
import refrigerator.back.mybookmark.application.dto.MyBookmarkPreviewDto;
import refrigerator.back.mybookmark.application.port.out.FindMyBookmarksPort;
import refrigerator.back.mybookmark.outbound.dto.OutMyBookmarkNumberDto;
import refrigerator.back.mybookmark.outbound.mapper.OutMyBookmarkDtoMapper;
import refrigerator.back.mybookmark.outbound.repository.query.MyBookmarkSelectQueryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MyBookmarkLookUpAdapter implements FindMyBookmarksPort {

    private final MyBookmarkSelectQueryRepository queryRepository;
    private final OutMyBookmarkDtoMapper mapper;
    private final ImageUrlConvert imageUrlConvert;

    @Override
    public List<MyBookmarkDto> findBookmarks(String memberId, int page, int size) {
        return queryRepository.selectMyBookmarks(memberId, PageRequest.of(page, size)).stream()
                .map(bookmark -> bookmark.mapping(mapper, imageUrlConvert))
                .collect(Collectors.toList());
    }

    @Override
    public List<MyBookmarkPreviewDto> findBookmarkPreviews(String memberId, int size) {
        return queryRepository.selectMyBookmarkPreviews(memberId, PageRequest.of(0, size)).stream()
                .map(bookmark -> bookmark.mapping(mapper, imageUrlConvert))
                .collect(Collectors.toList());
    }

}
