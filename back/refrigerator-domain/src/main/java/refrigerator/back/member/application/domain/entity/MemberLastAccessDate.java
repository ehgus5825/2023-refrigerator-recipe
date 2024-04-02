package refrigerator.back.member.application.domain.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

// TODO : 얘는 안쓰는 거 같은데..

@Entity
@Table(name = "member_last_access_date")
@Getter
public class MemberLastAccessDate {

    @Id
    @Column(name = "member_last_access_date_id", nullable = false)
    private String dateID;

    @Column(name = "access_date", nullable = false)
    private LocalDateTime accessDate;

}
