package refrigerator.back.recipe.application.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;

@Entity
@Table(name = "recipe_ingredient")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_ingredient_id")
    private Long ingredientId;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "volume", length = 30)
    private String volume;

    @Column(name = "trans_volume", nullable = false)
    private Double transVolume;

    @Column(name = "trans_unit", length = 20)
    private String transUnit;

    @Column(name = "type", nullable = false, length = 30)
    private String type;

}
