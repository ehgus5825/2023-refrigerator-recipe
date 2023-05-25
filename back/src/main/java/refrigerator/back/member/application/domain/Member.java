package refrigerator.back.member.application.domain;


import lombok.*;
import refrigerator.back.authentication.exception.AuthenticationExceptionType;
import refrigerator.back.global.common.BaseTimeEntity;
import refrigerator.back.global.exception.BusinessException;

import javax.persistence.*;
import java.util.Objects;
import java.util.Random;

@Entity
@Table(name = "member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member extends BaseTimeEntity {

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

    private Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.memberStatus = MemberStatus.STEADY_STATUS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(email, member.email) && Objects.equals(password, member.password) && Objects.equals(nickname, member.nickname) && memberStatus == member.memberStatus && profile == member.profile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, nickname, memberStatus, profile);
    }

    /* 비즈니스 로직 */

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }

    public void withdraw(){
        memberStatus = MemberStatus.LEAVE_STATUS;
    }

    private void initializeProfile(){
        this.profile = MemberProfileImage.pickUp();
    }
    public void initProfileAndNickname(String imageName, String nickname){
        this.profile = MemberProfileImage.findImageByName(imageName);
        this.nickname = nickname;
    }

    public static Member join(String email, String password, String nickname){
        Member member = new Member(email, password, nickname);
        member.initializeProfile();
        return member;
    }
}
