package refrigerator.back.word_completion.application.domain;

import org.springframework.stereotype.Component;
import refrigerator.back.word_completion.adapter.infra.JamoUtils;


@Component
public class WordParserUtilsImpl{
    public String split(String keyword) {
        return JamoUtils.split(keyword);
    }
}
