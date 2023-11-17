package refrigerator.back.recipe_searchword.infra;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

public class SearchWordPrefixedKeySerializer implements RedisSerializer<String> {

    private final String SEARCH_WORD_PREFIX = "SEARCH_WORD";


    @Override
    public byte[] serialize(String key) throws SerializationException {
        return (SEARCH_WORD_PREFIX + key).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null){
            return null;
        }
        String key = new String(bytes, StandardCharsets.UTF_8);
        if (key.startsWith(SEARCH_WORD_PREFIX)){
            return key.substring(SEARCH_WORD_PREFIX.length());
        }
        return key;
    }
}
