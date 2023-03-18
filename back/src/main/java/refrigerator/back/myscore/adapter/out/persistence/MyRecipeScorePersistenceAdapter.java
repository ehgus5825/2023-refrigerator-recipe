package refrigerator.back.myscore.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import refrigerator.back.myscore.adapter.out.dto.MyRecipeScoreMappingDTO;
import refrigerator.back.myscore.adapter.out.mapper.MyRecipeScoreMapper;
import refrigerator.back.myscore.adapter.out.entity.MyRecipeScore;
import refrigerator.back.myscore.adapter.out.repository.MyRecipeScoreRepository;
import refrigerator.back.myscore.application.domain.MyRecipeScoreDomain;
import refrigerator.back.myscore.application.port.out.MyRecipeScoreReadPort;
import refrigerator.back.myscore.application.port.out.MyRecipeScoreWritePort;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MyRecipeScorePersistenceAdapter implements MyRecipeScoreReadPort, MyRecipeScoreWritePort {

    @Autowired MyRecipeScoreRepository repository;
    @Autowired MyRecipeScoreMapper mapper;

    @Override
    public MyRecipeScoreDomain getMyRecipeScoreByID(Long scoreID) {
        MyRecipeScoreMappingDTO myScore = repository.findMyRecipeScoreById(scoreID);
        return mapper.dtoToDomain(myScore);
    }

    @Override
    public List<MyRecipeScoreDomain> getMyRecipeScoreList(String memberID, int page, int size) {
        return repository.findMyRecipeScoreList(memberID, PageRequest.of(page, size))
                .stream().map(mapper::dtoToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public MyRecipeScoreDomain getPersistenceMyRecipeById(Long scoreID) {
        MyRecipeScore entity = repository.findById(scoreID)
                .orElseThrow(RuntimeException::new);
        return mapper.toDomain(entity);
    }

    @Override
    public Long save(MyRecipeScoreDomain domain) {
        MyRecipeScore entity = mapper.toEntity(domain);
        repository.save(entity);
        return entity.getScoreID();
    }
}
