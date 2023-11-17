package refrigerator.back.recipe_searchword.infra;


import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import refrigerator.back.global.redis.config.SearchRedisLettuceConnectionConfig;

@Configuration
@EnableCaching
@RequiredArgsConstructor
@Import(SearchRedisLettuceConnectionConfig.class)
public class SearchWordRedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate<String, String> searchWordRedisTemplate(){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new SearchWordPrefixedKeySerializer());
        redisTemplate.setHashKeySerializer(new SearchWordPrefixedKeySerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }


}
