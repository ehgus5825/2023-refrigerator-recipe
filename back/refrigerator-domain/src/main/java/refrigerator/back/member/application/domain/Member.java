package refrigerator.back.member.application.domain;


import lombok.*;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile", nullable = false, length = 100)
    private MemberProfileImage profile;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime joinDateTime;

    @Builder
    private Member(String email, String password, String nickname,
                  MemberStatus memberStatus, MemberProfileImage profile,
                  LocalDateTime joinDateTime) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.memberStatus = memberStatus;
        this.profile = profile;
        this.joinDateTime = joinDateTime;
    }

    public static Member createForTest(String email, String password, String nickname,
                                        MemberStatus memberStatus, MemberProfileImage profile,
                                        LocalDateTime joinDateTime){
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .memberStatus(memberStatus)
                .profile(profile)
                .joinDateTime(joinDateTime)
                .build();
    }

    public static Member join(String email,
                              String password,
                              String nickname,
                              int profileImageRandomNumber,
                              LocalDateTime joinDateTime){
        MemberProfileImage profileImage = MemberProfileImage.pickUp(profileImageRandomNumber);
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .memberStatus(MemberStatus.STEADY_STATUS)
                .profile(profileImage)
                .joinDateTime(joinDateTime)
                .build();
    }

    public static void isDuplicatedByEmail(int number){
        if (number != 0){
            throw new BusinessException(MemberExceptionType.DUPLICATE_EMAIL);
        }
    }
}
