//package refrigerator.back.global.time;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Slf4j
//class CurrentTimeTest {
//
//    @Test
//    @DisplayName("LocalDateTime 타입의 CurrentTime 을 사용하는 경우")
//    void localDateTime(){
//
//        LocalDateTimeService timeService = new LocalDateTimeService(
//                () -> LocalDateTime.of(2024, 2, 3, 1, 1)
//        );
//
//        log.info("time={}", timeService.now());
//    }
//
//    @Test
//    @DisplayName("LocalDate 타입의 CurrentTime 을 사용하는 경우")
//    void dateTime(){
//
//        LocalDateService timeService = new LocalDateService(
//                () -> LocalDate.of(2024, 2, 3)
//        );
//
//        log.info("date={}", timeService.now());
//
//    }
//
//    @Test
//    @DisplayName("currentTime 의 반환 타입을 상황에 따라 설정하고 싶은 경우")
//    void genericTime(){
//
//        GenericTimeService<String> timeService = new GenericTimeService<>(
//                () -> "시간"
//        );
//
//        log.info("string={}", timeService.now());
//
//    }
//
//    @RequiredArgsConstructor
//    static class LocalDateTimeService{
//
//        private final CurrentTime<LocalDateTime> currentTime;
//
//        public LocalDateTime now(){
//            return currentTime.now();
//        }
//
//    }
//
//    @RequiredArgsConstructor
//    static class LocalDateService{
//
//        private final CurrentTime<LocalDate> currentTime;
//
//        public LocalDate now(){
//            return currentTime.now();
//        }
//    }
//
//    @RequiredArgsConstructor
//    static class GenericTimeService<T>{
//        private final CurrentTime<T> currentTime;
//
//        public T now(){
//            return currentTime.now();
//        }
//    }
//
//}