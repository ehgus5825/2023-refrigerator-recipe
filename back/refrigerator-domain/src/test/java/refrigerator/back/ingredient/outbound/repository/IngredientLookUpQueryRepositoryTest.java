package refrigerator.back.ingredient.outbound.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.outbound.dto.OutIngredientDTO;
import refrigerator.back.ingredient.application.domain.entity.Ingredient;
import refrigerator.back.ingredient.application.domain.value.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;
import refrigerator.back.ingredient.outbound.dto.OutMyIngredientDTO;
import refrigerator.back.ingredient.outbound.repository.query.IngredientLookUpQueryRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, IngredientLookUpQueryRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestDataInit("/ingredientImage.sql")
@Slf4j
class IngredientLookUpQueryRepositoryTest {

    @Autowired TestEntityManager em;

    @Autowired IngredientLookUpQueryRepository ingredientLookUpQueryRepository;

    @Test
    @DisplayName("식재료 목록 조회 테스트")
    void findIngredientListTest() {

        // given
        CurrentTime<LocalDate> currentTime = () -> LocalDate.of(2023, 1,1);

        String email = "email123@gmail.com";

        // 보관 방식 : FRIDGE 
        // deadline : false => 유통기한이 남아있는 식재료
        // email : email이 같은 사용자 식재료 목록에서 조회
        IngredientSearchCondition condition = IngredientSearchCondition.builder()
                .email(email)
                .storage(IngredientStorageType.FRIDGE)
                .deadline(false)
                .build();

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .registrationDate(currentTime.now())
                .capacity(30.0)
                .capacityUnit("g")
                .image(1)
                .deleted(false);

        // 조회 X (보관 방식이 FREEZER)
        em.persist(builder.name("감자").expirationDate(currentTime.now().plusDays(1))
                .storageMethod(IngredientStorageType.FREEZER).email(email).build());

        // 조회 X (유통기한이 지남)
        em.persist(builder.name("고구마").expirationDate(currentTime.now().minusDays(1))
                .storageMethod(IngredientStorageType.FRIDGE).email(email).build());

        // 조회 X (다른 이메일)
        em.persist(builder.name("호박").expirationDate(currentTime.now().plusDays(1))
                .storageMethod(IngredientStorageType.FRIDGE).email("email456@gmail.com").build());

        // 조회 O
        Long id = em.persistAndGetId(builder.name("수박").expirationDate(currentTime.now().plusDays(1))
                .storageMethod(IngredientStorageType.FRIDGE).email(email).build(), Long.class);

        // when
        List<OutIngredientDTO> list = ingredientLookUpQueryRepository.findIngredientList(currentTime.now(), condition, PageRequest.of(0, 5));

        // then
        assertThat(list.size()).isEqualTo(1);

        OutIngredientDTO outIngredientDTO = list.get(0);
        assertThat(outIngredientDTO.getIngredientID()).isEqualTo(id);
        assertThat(outIngredientDTO.getName()).isEqualTo("수박");
        assertThat(outIngredientDTO.getExpirationDate()).isEqualTo(currentTime.now().plusDays(1));
        assertThat(outIngredientDTO.getImageName()).isEqualTo("IMAGE_INGREDIENT_PROCESSED.png");
    }

    @Test
    @DisplayName("식재료 목록 조회 정렬 테스트 : deadline -> false / 유통기한이 남아있는 식재료")
    void findIngredientListSortTestByDeadlineFalse() {

        // given
        CurrentTime<LocalDate> currentTime = () -> LocalDate.of(2023, 1,1);

        String email = "email123@gmail.com";

        IngredientSearchCondition condition = IngredientSearchCondition.builder()
                .email(email)
                .storage(IngredientStorageType.FRIDGE)
                .deadline(false)
                .build();

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .registrationDate(currentTime.now())
                .capacity(30.0)
                .capacityUnit("g")
                .image(1)
                .deleted(false);

        Long id1 = em.persistAndGetId(builder.name("호박").expirationDate(currentTime.now().plusDays(3))
                .storageMethod(IngredientStorageType.FRIDGE).email(email).build(), Long.class);
        Long id2 = em.persistAndGetId(builder.name("가지").expirationDate(currentTime.now().plusDays(3))
                .storageMethod(IngredientStorageType.FRIDGE).email(email).build(), Long.class);
        Long id3 = em.persistAndGetId(builder.name("감자").expirationDate(currentTime.now().plusDays(1))
                .storageMethod(IngredientStorageType.FRIDGE).email(email).build(), Long.class);
        Long id4 = em.persistAndGetId(builder.name("수박").expirationDate(currentTime.now().plusDays(2))
                .storageMethod(IngredientStorageType.FRIDGE).email(email).build(), Long.class);

        // when
        List<OutIngredientDTO> list = ingredientLookUpQueryRepository.findIngredientList(currentTime.now(), condition, PageRequest.of(0, 5));

        // then

        // 유통기한이 남아있는 식재료 정렬 기준 : 유통기한 가까운순 -> 먼순 (오름차순), 식재료명 가나다순
        assertThat(list.get(0).getIngredientID()).isEqualTo(id3);
        assertThat(list.get(1).getIngredientID()).isEqualTo(id4);
        assertThat(list.get(2).getIngredientID()).isEqualTo(id2);
        assertThat(list.get(3).getIngredientID()).isEqualTo(id1);
    }

    @Test
    @DisplayName("식재료 목록 조회 정렬 테스트 : deadline -> true / 유통기한이 지난 식재료")
    void findIngredientListSortTestByDeadlineTrue() {

        // given
        CurrentTime<LocalDate> currentTime = () -> LocalDate.of(2023, 1,1);

        String email = "email123@gmail.com";

        IngredientSearchCondition condition = IngredientSearchCondition.builder()
                .email(email)
                .storage(IngredientStorageType.FRIDGE)
                .deadline(true)
                .build();

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .registrationDate(currentTime.now())
                .capacity(30.0)
                .capacityUnit("g")
                .image(1)
                .deleted(false);

        Long id1 = em.persistAndGetId(builder.name("호박").expirationDate(currentTime.now().minusDays(3))
                .storageMethod(IngredientStorageType.FRIDGE).email(email).build(), Long.class);
        Long id2 = em.persistAndGetId(builder.name("가지").expirationDate(currentTime.now().minusDays(3))
                .storageMethod(IngredientStorageType.FRIDGE).email(email).build(), Long.class);
        Long id3 = em.persistAndGetId(builder.name("감자").expirationDate(currentTime.now().minusDays(1))
                .storageMethod(IngredientStorageType.FRIDGE).email(email).build(), Long.class);
        Long id4 = em.persistAndGetId(builder.name("수박").expirationDate(currentTime.now().minusDays(2))
                .storageMethod(IngredientStorageType.FRIDGE).email(email).build(), Long.class);

        // when
        List<OutIngredientDTO> list = ingredientLookUpQueryRepository.findIngredientList(currentTime.now(), condition, PageRequest.of(0, 5));

        // then

        // 유통기한이 지난 식재료 정렬 기준 : 유통기한 가까운순 -> 먼순 (내림차순), 식재료명 가나다순
        assertThat(list.get(0).getIngredientID()).isEqualTo(id3);
        assertThat(list.get(1).getIngredientID()).isEqualTo(id4);
        assertThat(list.get(2).getIngredientID()).isEqualTo(id2);
        assertThat(list.get(3).getIngredientID()).isEqualTo(id1);
    }

    @Test
    @DisplayName("임박 식재료 조회 테스트")
    void findIngredientListByDeadlineTest() {

        // given
        CurrentTime<LocalDate> currentTime = () -> LocalDate.of(2023, 1,1);

        String email = "email123@gmail.com";

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .registrationDate(currentTime.now())
                .capacity(30.0)
                .capacityUnit("g")
                .image(1)
                .deleted(false);

        // 유통기한 1일 남은 식재료
        Long id = em.persistAndGetId(builder.name("감자").expirationDate(currentTime.now().plusDays(1))
                .storageMethod(IngredientStorageType.FREEZER).email(email).build(), Long.class);

        // 유통기한 3일 남은 식재료
        em.persist(builder.name("고구마").expirationDate(currentTime.now().plusDays(3))
                .storageMethod(IngredientStorageType.FREEZER).email(email).build());

        // when
        // 유통기한 1일 남은 식재료 조회
        List<OutIngredientDTO> list = ingredientLookUpQueryRepository.findIngredientListByDeadline(currentTime.now(), 1L, email);

        // then
        assertThat(list.size()).isEqualTo(1);

        OutIngredientDTO outIngredientDTO = list.get(0);
        assertThat(outIngredientDTO.getIngredientID()).isEqualTo(id);
        assertThat(outIngredientDTO.getName()).isEqualTo("감자");
        assertThat(outIngredientDTO.getExpirationDate()).isEqualTo(currentTime.now().plusDays(1));
        assertThat(outIngredientDTO.getImageName()).isEqualTo("IMAGE_INGREDIENT_PROCESSED.png");
    }

    @Test
    @DisplayName("이메일에 따른 전체 식재료 조회 테스트")
    void findIngredientListOfAllTest() {

        // given
        CurrentTime<LocalDate> currentTime = () -> LocalDate.of(2023, 1,1);

        String email = "email123@gmail.com";

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .registrationDate(currentTime.now())
                .capacity(30.0)
                .capacityUnit("g")
                .image(1)
                .deleted(false);

        // 같은 이메일 (조회 O)
        Long id = em.persistAndGetId(builder.name("감자").expirationDate(currentTime.now())
                .storageMethod(IngredientStorageType.FREEZER).email(email).build(), Long.class);

        // 다른 이메일 (조회 X)
        em.persist(builder.name("고구마").expirationDate(currentTime.now())
                .storageMethod(IngredientStorageType.FREEZER).email("email456@gmail.com").build());

        // when
        List<OutIngredientDTO> list = ingredientLookUpQueryRepository.findIngredientListOfAll(email);

        // then
        assertThat(list.size()).isEqualTo(1);

        OutIngredientDTO outIngredientDTO = list.get(0);
        assertThat(outIngredientDTO.getIngredientID()).isEqualTo(id);
        assertThat(outIngredientDTO.getName()).isEqualTo("감자");
        assertThat(outIngredientDTO.getExpirationDate()).isEqualTo(currentTime.now());
        assertThat(outIngredientDTO.getImageName()).isEqualTo("IMAGE_INGREDIENT_PROCESSED.png");
    }

    @Test
    @DisplayName("식재료 단건 조회 테스트")
    void findIngredientTest() {

        // given
        CurrentTime<LocalDate> currentTime = () -> LocalDate.of(2023, 1,1);

        Ingredient ingredient = Ingredient.builder()
                .name("감자")
                .expirationDate(currentTime.now())
                .registrationDate(currentTime.now())
                .capacity(30.0)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .storageMethod(IngredientStorageType.FREEZER)
                .email("email123@gmail.com")
                .build();

        Long id = em.persistAndGetId(ingredient, Long.class);

        // when, then
        ingredientLookUpQueryRepository.findIngredient(id).ifPresent(
                dto -> {
                    log.info("enter");
                    assertThat(dto.getIngredientID()).isEqualTo(id);
                    assertThat(dto.getName()).isEqualTo("감자");
                    assertThat(dto.getUnit()).isEqualTo("g");
                    assertThat(dto.getVolume()).isEqualTo(30.0);
                    assertThat(dto.getRegistrationDate()).isEqualTo(currentTime.now());
                    assertThat(dto.getExpirationDate()).isEqualTo(currentTime.now());
                    assertThat(dto.getImageName()).isEqualTo("IMAGE_INGREDIENT_PROCESSED.png");
                    assertThat(dto.getStorage()).isEqualTo(IngredientStorageType.FREEZER);
                }
        );
    }

    @Test
    @DisplayName("사용 가능한 식재료 목록 조회")
    void findAvailableIngredientsTest(){

        CurrentTime<LocalDate> currentTime = () -> LocalDate.of(2023, 1,1);

        String email = "email123@gmail.com";

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .registrationDate(currentTime.now())
                .capacity(30.0)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .storageMethod(IngredientStorageType.FREEZER)
                .email(email);

        em.persist(builder.name("감자").expirationDate(currentTime.now().plusDays(5)).build());
        em.persist(builder.name("당근").expirationDate(currentTime.now().minusDays(5)).build());

        List<OutMyIngredientDTO> list = ingredientLookUpQueryRepository.findAvailableIngredients(email, currentTime.now());

        assertThat(list.size()).isEqualTo(1);

        OutMyIngredientDTO dto = list.get(0);
        assertThat(dto.getName()).isEqualTo("감자");
        assertThat(dto.getVolume()).isEqualTo(30.0);
        assertThat(dto.getUnit()).isEqualTo("g");
    }

    @Test
    @DisplayName("이메일 체크 테스트")
    void emailCheckTest() {

        assertThatThrownBy(() -> ingredientLookUpQueryRepository.emailCheck(null))
                .isInstanceOf(BusinessException.class);

        assertThatThrownBy(() -> ingredientLookUpQueryRepository.emailCheck(""))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("id 체크 테스트")
    void idCheckTest() {
        assertThatThrownBy(() -> ingredientLookUpQueryRepository.idCheck(null))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("저장방식 체크 테스트")
    void storageCheckTest() {
        assertThatThrownBy(() -> ingredientLookUpQueryRepository.storageCheck(null))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("유통기한별 조회 방식 날짜 체크 테스트")
    void deadlineCheckTest() {
        assertThatThrownBy(() -> ingredientLookUpQueryRepository.deadlineCheck(null, false))
                .isInstanceOf(BusinessException.class);
    }
}