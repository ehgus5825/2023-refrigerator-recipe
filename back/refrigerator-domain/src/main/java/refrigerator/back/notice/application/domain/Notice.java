package refrigerator.back.notice.application.domain;

import lombok.*;
import refrigerator.back.global.common.BaseTimeEntity;

import javax.persistence.*;

//@Entity
//@Table(name = "notice")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Notice extends BaseTimeEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "notice_id")
    private Long id;

//    @Column(name = "title", nullable = false, length = 400)
    private String title;

//    @Lob
//    @Column(name = "content", nullable = false)
    private String content;

}
