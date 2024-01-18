package refrigerator.back.recipe.application.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "recipe_course")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RecipeCourse implements Serializable {

    @Id
    @Column(name = "recipe_course_id")
    private Long courseId;

    @Column(name = "step")
    private Integer step;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "image")
    private String imageName;

    @Column(name = "recipe_id")
    private Long recipeId;

}
