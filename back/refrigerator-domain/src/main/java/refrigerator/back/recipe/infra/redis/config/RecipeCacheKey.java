package refrigerator.back.recipe.infra.redis.config;

public class RecipeCacheKey {
    public static final String FOOD_TYPE = "FOOD_TYPE";
    public static final String CATEGORY = "CATEGORY";
    public static final String RECIPE = "RECIPE";
    public static final String RECIPE_INGREDIENT_AND_COURSE = "RECIPE_INGREDIENT_AND_COURSE";
    public static final String RECIPE_NAME = "RECIPE_NAME";
    public static final int RECIPE_EXPIRE_SEC = 30;
    public static final int CONDITION_EXPIRE_SEC = 24 * 60 * 60;
    public static final int RECIPE_NAME_SEC = 60 * 60 * 24;
    public static final int DEFAULT_EXPIRE_SEC = 30 * 30;
    public static final int RECIPE_INGREDIENT_AND_COURSE_SEC = 24 * 60 * 60;
}
