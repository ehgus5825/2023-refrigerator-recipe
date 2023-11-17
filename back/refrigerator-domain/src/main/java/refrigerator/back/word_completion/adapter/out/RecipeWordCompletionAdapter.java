package refrigerator.back.word_completion.adapter.out;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.ingredient.infra.redis.IngredientCacheKey;
import refrigerator.back.recipe.infra.redis.config.RecipeCacheKey;
import refrigerator.back.word_completion.application.port.out.FindIngredientNamesPort;


import java.util.List;

import static refrigerator.back.ingredient.application.domain.QRegisteredIngredient.registeredIngredient;
import static refrigerator.back.recipe.application.domain.entity.QRecipe.recipe;


@Component
@RequiredArgsConstructor
public class RecipeWordCompletionAdapter implements FindIngredientNamesPort {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Cacheable(value = RecipeCacheKey.RECIPE_NAME,
            key = "'recipe_name_for_recommend'",
            cacheManager = "recipeNameCacheManagerForWordCompletion")
    public List<String> getNamesByRecipe() {
        return jpaQueryFactory
                .select(recipe.recipeName)
                .from(recipe)
                .fetch();
    }

    @Override
    @Cacheable(value = IngredientCacheKey.REGISTERED_INGREDIENT,
            key = "'registered_ingredient_name'",
            cacheManager = "registeredIngredientCacheManager")
    public List<String> getNamesByRegisteredIngredient() {
        return jpaQueryFactory
                .select(registeredIngredient.name)
                .from(registeredIngredient)
                .fetch();
    }

}
