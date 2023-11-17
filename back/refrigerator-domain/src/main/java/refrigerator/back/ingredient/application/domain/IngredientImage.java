package refrigerator.back.ingredient.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ingredient_image")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientImage {

    @Id
    @Column(name = "ingredient_image_id")
    private Integer id;

    @Column(name = "ingredient_image_type_name", nullable = false, length = 80)
    private String typeName;

    @Column(name = "ingredient_image_file_name", nullable = false)
    private String imageFileName;

}
