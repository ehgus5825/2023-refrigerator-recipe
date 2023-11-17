package refrigerator.back.recipe_searchword.application.port.in;

public interface DeleteSearchWordUseCase {
    void delete(String memberId, String value);
}
