package refrigerator.back.ingredient.infra.redis;

public class IngredientCacheKey {
    public static final String REGISTERED_INGREDIENT = "REGISTERED_INGREDIENT";
    public static final int REGISTERED_INGREDIENT_SEC = 60 * 60 * 24;
    public static final int DEFAULT_EXPIRE_SEC = 30;
}
