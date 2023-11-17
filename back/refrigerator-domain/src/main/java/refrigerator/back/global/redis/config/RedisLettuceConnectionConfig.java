package refrigerator.back.global.redis.config;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

public abstract class RedisLettuceConnectionConfig {

    abstract public RedisConnectionFactory redisConnectionFactory();

    protected RedisConnectionFactory createConnection(String host, int port, String password){
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
        redisConfiguration.setHostName(host);
        redisConfiguration.setPort(port);
        redisConfiguration.setPassword(password);
        return new LettuceConnectionFactory(redisConfiguration);
    }
}
