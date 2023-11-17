package refrigerator.back.ingredient.infra.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class IngredientCacheConfig {

    @Bean
    public RedisCacheManager registeredIngredientCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration configuration = getConfiguration();

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(IngredientCacheKey.REGISTERED_INGREDIENT, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(IngredientCacheKey.REGISTERED_INGREDIENT_SEC)));


        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }


    private RedisCacheConfiguration getConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(IngredientCacheKey.DEFAULT_EXPIRE_SEC)) // 캐시의 기본 유효시간 설정
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new StringRedisSerializer()));
    }

}
