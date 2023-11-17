package refrigerator.back.comment.application.port.out;

public interface FindNumberOfCommentsPort {
    Integer getNumber(Long recipeId);
}
