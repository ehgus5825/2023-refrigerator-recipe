package refrigerator.back.ingredient.application.domain;


import lombok.*;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.exception.IngredientExceptionType;

import javax.persistence.*;
import java.time.LocalDate;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

@Entity
@Table(name = "ingredient")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "capacity", nullable = false, length = 30)
    private Double capacity;

    @Column(name = "capacity_unit", nullable = false, length = 30)
    private String capacityUnit;

    @Column(name = "storage_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private IngredientStorageType storageMethod;

    @Column(name = "image", nullable = false)
    private Integer image;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "email", nullable = false)
    private String email;

    public void delete(){
        this.deleted = true;
    }

    public void undelete() {
        this.deleted = false;
    }

    public Ingredient(String name, LocalDate expirationDate, LocalDate registrationDate, Double capacity, String capacityUnit, IngredientStorageType storageMethod, Integer imageId, String email) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.capacity = capacity;
        this.capacityUnit = capacityUnit;
        this.storageMethod = storageMethod;
        this.registrationDate = registrationDate;
        this.image = imageId;
        this.email = email;
    }

    public static Ingredient create(String name, LocalDate expirationDate, LocalDate registrationDate, Double capacity, String capacityUnit, IngredientStorageType storageMethod, Integer imageId, String email) {
        capacityCheck(capacity);
        Ingredient ingredient = Ingredient.builder()
                .name(name)
                .expirationDate(expirationDate)
                .registrationDate(registrationDate)
                .capacity(capacity)
                .capacityUnit(capacityUnit)
                .storageMethod(storageMethod)
                .image(imageId)
                .email(email)
                .build();

        ingredient.undelete();
        return ingredient;
    }

    public void modify(LocalDate expirationDate, Double capacity, IngredientStorageType storageMethod) {
        capacityCheck(capacity);
        this.expirationDate = expirationDate;
        this.capacity = capacity;
        this.storageMethod = storageMethod;
    }

    public static void capacityCheck(Double capacity) {
        if(capacity > 9999.9 || capacity <= 0.0) {
            throw new BusinessException(EXCEEDED_CAPACITY_RANGE);
        }
    }

    public void deductionVolume(Double volume) {
        capacityCheck(volume);

        if (capacity < volume){
            this.capacity = 0.0;
            return;
        }
        this.capacity -= volume;
    }
}
