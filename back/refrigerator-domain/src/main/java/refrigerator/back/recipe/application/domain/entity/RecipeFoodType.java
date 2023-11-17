package refrigerator.back.recipe.application.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "recipe_food_type")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeFoodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_food_type_id")
    private Integer typeID;

    @Column(name = "recipe_food_type", nullable = false, length = 35)
    private String typeName;

}
