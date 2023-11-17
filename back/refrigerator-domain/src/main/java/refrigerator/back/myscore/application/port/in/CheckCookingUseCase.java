package refrigerator.back.myscore.application.port.in;

public interface CheckCookingUseCase {
    Boolean isCooked(Long recipeId, String memberId);
}
