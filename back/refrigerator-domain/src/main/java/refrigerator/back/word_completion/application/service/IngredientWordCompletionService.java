package refrigerator.back.word_completion.application.service;

import org.springframework.stereotype.Service;
import refrigerator.back.word_completion.application.domain.WordCompletionTrie;
import refrigerator.back.word_completion.application.domain.WordFormatValidation;
import refrigerator.back.word_completion.application.port.in.IngredientWordCompletionUseCase;
import refrigerator.back.word_completion.application.port.out.FindIngredientNamesPort;


import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientWordCompletionService implements IngredientWordCompletionUseCase {

    private final FindIngredientNamesPort findIngredientNamesPort;
    private final WordCompletionTrie ingredientTrie;
    private final WordFormatValidation wordFormat;


    public IngredientWordCompletionService(FindIngredientNamesPort findIngredientNamesPort) {
        this.findIngredientNamesPort = findIngredientNamesPort;
        this.ingredientTrie = new WordCompletionTrie();
        this.wordFormat = new WordFormatValidation();
    }


    @Override
    public List<String> search(String keyword) {
        init();
        if (!wordFormat.wordCheck(keyword)){
            return new ArrayList<>();
        }
        return ingredientTrie.search(keyword);
    }

    private void init(){
        if (this.ingredientTrie.isBlank()){
            List<String> words = findIngredientNamesPort.getNamesByRegisteredIngredient();
            words.forEach(ingredientTrie::insert);
        }
    }

}
