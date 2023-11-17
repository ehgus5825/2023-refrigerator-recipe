package refrigerator.back.global.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class SearchRedisLettuceConnectionConfig extends RedisLettuceConnectionConfig{

    @Value("${spring.redis.search.host}")
    private String host;

    @Value("${spring.redis.search.port}")
    private int port;

    @Value("${spring.redis.search.password}")
    private String password;

    @Override
    @Bean("searchWordRedisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {
        return createConnection(host, port, password);
    }
}
