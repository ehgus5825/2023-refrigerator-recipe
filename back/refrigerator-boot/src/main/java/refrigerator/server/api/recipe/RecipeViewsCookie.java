package refrigerator.server.api.recipe;


import javax.servlet.http.Cookie;
import java.util.Arrays;

public class RecipeViewsCookie {

    private final int maxAge = 24 * 60 * 60;
    private final String name = "Recipe-View";
    private final String path = "/api/recipe";

    public boolean isViewed(Long recipeId, Cookie[] cookies){
        if (cookies != null){
            String targetValue = String.valueOf(recipeId);
            return Arrays.stream(cookies).anyMatch(target -> isEquals(targetValue, target));
        }
        return false;
    }

    public Cookie create(Long recipeId){
        Cookie cookie = new Cookie(name, String.valueOf(recipeId));
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true);
        return cookie;
    }

    private boolean isEquals(String targetValue, Cookie target){
        return target.getName().equals(name) && target.getValue().equals(targetValue);
    }

}
