package refrigerator.back.word_completion.application.service;

import org.springframework.stereotype.Service;
import refrigerator.back.word_completion.application.domain.WordCompletionTrie;
import refrigerator.back.word_completion.application.domain.WordFormatValidation;
import refrigerator.back.word_completion.application.port.in.RecipeWordCompletionUseCase;
import refrigerator.back.word_completion.application.port.out.FindIngredientNamesPort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecipeWordCompletionService implements RecipeWordCompletionUseCase {

    private final FindIngredientNamesPort findIngredientNamesPort;
    private final WordCompletionTrie recipeTrie;
    private final WordFormatValidation formatValidation;

    public RecipeWordCompletionService(FindIngredientNamesPort findIngredientNamesPort) {
        this.findIngredientNamesPort = findIngredientNamesPort;
        this.recipeTrie = new WordCompletionTrie();
        this.formatValidation = new WordFormatValidation();
    }

    @Override
    public List<String> search(String keyword) {
        init();
        if (!formatValidation.wordCheck(keyword)){
            return new ArrayList<>();
        }
        return recipeTrie.search(keyword);
    }

    private void init(){
        if (recipeTrie.isBlank()){
            Set<String> words = getRecipeWordCompletionList();
            words.forEach(recipeTrie::insert);
        }
    }

    private Set<String> getRecipeWordCompletionList(){
        Set<String> result = new HashSet<>();
        result.addAll(findIngredientNamesPort.getNamesByRecipe());
        result.addAll(findIngredientNamesPort.getNamesByRegisteredIngredient());
        return result;
    }
}
