package refrigerator.back.word_completion.application.domain;

import org.springframework.stereotype.Component;


@Component
public class WordParserUtils {
    public String split(String keyword) {
        return JamoUtils.split(keyword);
    }
}
