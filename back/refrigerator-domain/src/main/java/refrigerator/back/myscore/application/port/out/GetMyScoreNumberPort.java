package refrigerator.back.myscore.application.port.out;

public interface GetMyScoreNumberPort {
    Integer getByMemberId(String memberId);
    Integer getByRecipeIdAndMemberId(Long recipeId, String memberId);
}
