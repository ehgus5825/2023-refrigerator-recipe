package refrigerator.back.recipe.application.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "recipe_views")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeViews {

    @Id
    @Column(name = "recipe_id")
    private Long recipeID;

    @Column(name = "views")
    private int views;

    @Version
    private Long version;

}
