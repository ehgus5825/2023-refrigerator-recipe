package refrigerator.back.recipe.application.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "recipe")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "recipe_name", nullable = false, length = 60)
    private String recipeName;

    @Column(name = "description", nullable = false, length = 350)
    private String description;

    @Column(name = "cooking_time", nullable = false)
    private Integer cookingTime;

    @Column(name = "kcal", nullable = false)
    private Integer kcal;

    @Column(name = "servings", nullable = false)
    private Integer servings;

    @Column(name = "difficulty", nullable = false, length = 10)
    private String difficulty;

    @Column(name = "recipe_type", nullable = false, length = 20)
    private String recipeType;

    @Column(name = "food_type")
    private Integer recipeFoodType;

    @Column(name = "recipe_category")
    private Integer recipeCategory;

    @Column(name = "main_image", nullable = false)
    private String image;

}