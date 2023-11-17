package refrigerator.back.recipe_searchword.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe_searchword.application.domain.SearchWordType;
import refrigerator.back.recipe_searchword.application.port.in.AddSearchWordUseCase;
import refrigerator.back.recipe_searchword.application.port.in.DeleteSearchWordUseCase;
import refrigerator.back.recipe_searchword.application.port.in.FindLastSearchWordUseCase;
import refrigerator.back.recipe_searchword.application.port.out.*;
import refrigerator.back.recipe_searchword.exception.SearchWordExceptionType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LastSearchWordService implements AddSearchWordUseCase, DeleteSearchWordUseCase, FindLastSearchWordUseCase {

    private final AddSearchWordPort addSearchWordPort;
    private final DeleteOldSearchWordPort deleteOldSearchWordPort;
    private final DeleteSearchWordPort deleteSearchWordPort;
    private final GetSearchWordListSizePort getSearchWordListSizePort;
    private final FindSearchWordPort findSearchWordPort;

    @Override
    public void addSearchWord(String memberId, String searchWord) {
        String key = makeLastSearchWordKey(memberId);
        Integer size = getSearchWordListSizePort.getWordListSize(key);
        if (size >= 5){
            deleteOldSearchWordPort.deleteOldWord(key);
        }
        addSearchWordPort.add(key, searchWord);
    }

    @Override
    public void delete(String memberId, String value) {
        String key = makeLastSearchWordKey(memberId);
        Integer size = getSearchWordListSizePort.getWordListSize(key);
        if (size >= 1){
            deleteSearchWordPort.delete(key, value);
            return;
        }
        throw new BusinessException(SearchWordExceptionType.EMPTY_WORD_LIST);
    }

    @Override
    public List<String> getLastSearchWords(String memberId) {
        return findSearchWordPort.findSearchWord(makeLastSearchWordKey(memberId));
    }

    private String makeLastSearchWordKey(String memberId) {
        return SearchWordType.LAST_SEARCH_WORD.getType() + "_" + memberId;
    }

}
