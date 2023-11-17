package refrigerator.back.global.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class BusinessRedisLettuceConnectionConfig extends RedisLettuceConnectionConfig{

    @Value("${spring.redis.basic.host}")
    private String host;

    @Value("${spring.redis.basic.port}")
    private int port;

    @Value("${spring.redis.basic.password}")
    private String password;

    @Override
    @Bean(name = "businessRedisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {
        return createConnection(host, port, password);
    }
}
