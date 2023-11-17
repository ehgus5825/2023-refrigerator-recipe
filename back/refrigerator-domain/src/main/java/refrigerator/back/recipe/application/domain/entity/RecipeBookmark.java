package refrigerator.back.recipe.application.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recipe_bookmark")
@Getter
@NoArgsConstructor
public class RecipeBookmark {

    @Id
    @Column(name = "recipe_id")
    private Long recipeID;

    @Column(name = "count", nullable = false)
    private int count;

}
