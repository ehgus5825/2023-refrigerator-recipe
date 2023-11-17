package refrigerator.back.ingredient.application.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "registered_ingredient")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisteredIngredient implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registered_ingredient_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "unit", nullable = false, length = 20)
    private String unit;

    @Column(name = "image", nullable = false)
    private Integer image;

}
