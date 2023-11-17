package refrigerator.back.recipe_searchword.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchWordType {
    LAST_SEARCH_WORD("last"),
    RECOMMEND_SEARCH_WORD("recommend")
    ;

    private final String type;
}
