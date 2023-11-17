package refrigerator.back.global.time;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.comment.application.service.CommentTimeService;
import refrigerator.back.notification.application.domain.NotificationTimeService;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class TimeService implements CommentTimeService, NotificationTimeService {

    private final CurrentTime<LocalDateTime> currentTime;

    @Override
    public String replace(LocalDateTime date){
        LocalDateTime now = currentTime.now();
        Duration duration = Duration.between(date, now);

        long days = ChronoUnit.DAYS.between(date.toLocalDate(), now.toLocalDate());
        long months = ChronoUnit.MONTHS.between(date.toLocalDate(), now.toLocalDate());
        long years = ChronoUnit.YEARS.between(date.toLocalDate(), now.toLocalDate());

        if(duration.toMinutes()<1){ return duration.toSeconds() + " 초 전";}
        if(duration.toHours()<1){ return duration.toMinutes() + " 분 전";}
        if(duration.toDays()<1){ return duration.toHours() + " 시간 전";}

        if(months<1){ return days + " 일 전";}
        if(years<1) { return months + " 달 전";}

        return years + " 년 전";
    }
}
