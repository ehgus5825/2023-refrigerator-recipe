package refrigerator.back.recipe_searchword.adapter.out;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.exception.RecipeExceptionType;
import refrigerator.back.recipe_searchword.application.port.out.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class LastSearchWordAdapter implements
        AddSearchWordPort, DeleteOldSearchWordPort, DeleteSearchWordPort,
        FindSearchWordPort, GetSearchWordListSizePort {

    private final RedisTemplate<String, String> searchWordRedisTemplate;

    public LastSearchWordAdapter(@Qualifier("searchWordRedisTemplate") RedisTemplate<String, String> searchWordRedisTemplate){
        this.searchWordRedisTemplate = searchWordRedisTemplate;
    }

    @Override
    public void add(String key, String searchWord) {
        int size = getSize(key);
        searchWordRedisTemplate.opsForZSet().add(key, searchWord, size + 1);
    }

    @Override
    public void deleteOldWord(String key) {
        try{
            searchWordRedisTemplate.opsForZSet().popMin(key);
        }catch (RedisSystemException e){
            processException(key);
            throw new BusinessException(RecipeExceptionType.REDIS_TYPE_ERROR);
        }
    }

    @Override
    public void delete(String key, String deleteValue) {
        try{
            searchWordRedisTemplate.opsForZSet().remove(key, 1, deleteValue);
        } catch (RedisSystemException e){
            processException(key);
            throw new BusinessException(RecipeExceptionType.REDIS_TYPE_ERROR);
        }
    }

    @Override
    public List<String> findSearchWord(String key) {
        try{
            Set<String> result = searchWordRedisTemplate.opsForZSet().reverseRange(key, 0, getSize(key));
            if (result == null){
                return new ArrayList<>();
            }
            return new ArrayList<>(result);
        }catch (RedisSystemException e){
            processException(key);
        }
        throw new BusinessException(RecipeExceptionType.REDIS_TYPE_ERROR);
    }

    @Override
    public Integer getWordListSize(String key) {
        try{
            return getSize(key);
        }catch (RedisSystemException e){
            processException(key);
        }
        throw new BusinessException(RecipeExceptionType.REDIS_TYPE_ERROR);
    }

    private void processException(String key) {
        searchWordRedisTemplate.delete(key);
    }

    private int getSize(String key) {
        return Optional.ofNullable(searchWordRedisTemplate.opsForZSet().size(key))
                .orElse(0L).intValue();
    }
}
