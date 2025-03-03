package hello.myshop.biz.user.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.myshop.biz.user.dto.UserResponse;
import hello.myshop.biz.user.entity.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public UserResponse.ListResponse getUsers(String srchTyp, String srchTxt, Pageable pageable) {
//        List<User> users = queryFactory.selectFrom(QUser.user)
//                .fetch();
        QUser quser = QUser.user;
        List<UserResponse> users = queryFactory.select(
                        Projections.fields(UserResponse.class,
                                quser.id,
                                quser.email,
                                quser.username,
                                quser.role
                        ))
                .from(quser)
                .where(usernameLikeCond(srchTyp, srchTxt), emailLikeCond(srchTyp, srchTxt), roleLikeCond(srchTyp, srchTxt))
                .orderBy(quser.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(quser.count())
                .from(quser)
                .where(usernameLikeCond(srchTyp, srchTxt), emailLikeCond(srchTyp, srchTxt), roleLikeCond(srchTyp, srchTxt))
                .fetchOne();

        return UserResponse.ListResponse.builder()
                .users(users)
                .cnt(total)
                .size(pageable.getPageSize())
                .page(pageable.getOffset())
                .build();
    }


    public BooleanExpression usernameLikeCond(String srchTyp, String srchTxt) {
        QUser quser = QUser.user;
        if (srchTyp != null) {
            if (srchTyp.equals("username")) {
                return quser.username.like("%" + srchTxt + "%");
            }
        }
        return null;
    }

    public BooleanExpression emailLikeCond(String srchTyp, String srchTxt) {
        QUser quser = QUser.user;
        if (srchTyp != null) {
            if (srchTyp.equals("email")) {
                return quser.email.like("%" + srchTxt + "%");
            }
        }
        return null;
    }

    public BooleanExpression roleLikeCond(String srchTyp, String srchTxt) {
        QUser quser = QUser.user;
        if ("role".equals(srchTyp) && srchTxt != null) {
            return quser.role.stringValue().like("%" + srchTxt + "%"); // Enum 값을 문자열로 변환 후 비교
        }
        return null;
    }

}
