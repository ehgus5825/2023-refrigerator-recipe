package refrigerator.back.recipe_searchword.application.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
public class Ingredient {
    private String name;
    private LocalDate date;
    
    public boolean isExpired(LocalDate now){
        long between = ChronoUnit.DAYS.between(this.date, now);

        return between >= 0;
    }
}
