package refrigerator.back.authentication.outbound.repository;

import com.querydsl.core.NonUniqueResultException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.authentication.outbound.dto.OutUserDto;
import refrigerator.back.authentication.exception.UserRepositoryException;
import refrigerator.back.authentication.outbound.dto.QOutUserDto;
import refrigerator.back.member.exception.MemberExceptionType;

import java.util.Optional;

import static refrigerator.back.member.application.domain.QMember.*;

@Repository
@RequiredArgsConstructor
public class UserSelectQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<OutUserDto> selectUserByName(String username){
        try {
            return Optional.ofNullable(jpaQueryFactory
                    .select(new QOutUserDto(
                            member.email,
                            member.password,
                            member.memberStatus.stringValue()))
                    .from(member)
                    .where(member.email.eq(username))
                    .fetchOne());
        } catch(NonUniqueResultException e){
            throw new UserRepositoryException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
    }
}
