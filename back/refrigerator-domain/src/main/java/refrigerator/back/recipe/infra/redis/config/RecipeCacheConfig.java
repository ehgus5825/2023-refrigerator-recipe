package refrigerator.back.recipe.infra.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class RecipeCacheConfig {

    @Bean
    @Primary
    public RedisCacheManager recipeCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration configuration = getConfiguration();

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(RecipeCacheKey.RECIPE, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(RecipeCacheKey.RECIPE_EXPIRE_SEC)));


        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }

    @Bean
    public RedisCacheManager recipeIngredientAndCourse(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration configuration = getConfiguration();

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(RecipeCacheKey.RECIPE_INGREDIENT_AND_COURSE, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(RecipeCacheKey.RECIPE_INGREDIENT_AND_COURSE_SEC)));


        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }

    @Bean
    public RedisCacheManager recipeNameCacheManagerForWordCompletion(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration configuration = getConfiguration();

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(RecipeCacheKey.RECIPE_NAME, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(RecipeCacheKey.RECIPE_NAME_SEC)));


        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }

    private RedisCacheConfiguration getConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(RecipeCacheKey.DEFAULT_EXPIRE_SEC)) // 캐시의 기본 유효시간 설정
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new StringRedisSerializer()));
    }

}
