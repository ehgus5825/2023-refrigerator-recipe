package refrigerator.back.word_completion.application.domain;

import lombok.Getter;

import static java.util.regex.Pattern.matches;

@Getter
public class WordFormatValidation {

    private final String KEYWORD_REGEX = "^[가-힣ㄱ-ㅎ]+";

    public boolean wordCheck(String word) {
        return matches(KEYWORD_REGEX, word);
    }
}
