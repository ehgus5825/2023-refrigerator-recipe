package refrigerator.back.mybookmark.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.mybookmark.application.port.out.GetNumberOfMyBookmarkPort;
import refrigerator.back.mybookmark.outbound.dto.OutMyBookmarkNumberDto;
import refrigerator.back.mybookmark.outbound.repository.query.MyBookmarkSelectQueryRepository;

@Repository
@RequiredArgsConstructor
public class MyBookmarkCountAdapter implements GetNumberOfMyBookmarkPort {

    private final MyBookmarkSelectQueryRepository queryRepository;

    @Override
    public Integer getByRecipeIdAndMemberId(Long recipeId, String memberId) {
        OutMyBookmarkNumberDto numberDto = queryRepository.selectNumberOfMyBookmarkByRecipeIdAndMemberId(recipeId, memberId);
        return numberDto.getNumber();
    }

    @Override
    public Integer getByMemberId(String memberId) {
        OutMyBookmarkNumberDto numberDto = queryRepository.selectNumberOfMyBookmarks(memberId);
        return numberDto.getNumber();
    }
}
