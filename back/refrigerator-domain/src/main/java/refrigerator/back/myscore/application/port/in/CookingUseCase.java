package refrigerator.back.myscore.application.port.in;


public interface CookingUseCase {
    Long cooking(String memberId, Long recipeId, Double score);
}
