package refrigerator.back.searchword.application.port.in;

import java.util.List;

public interface FindRecommendSearchWordUseCase {
    List<String> getRecommendSearchWords(String memberId);
}
