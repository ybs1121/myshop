package hello.myshop.biz.user.repository.query;

import hello.myshop.biz.user.dto.UserResponse;
import org.springframework.data.domain.Pageable;

public interface UserQueryRepository {

    UserResponse.ListResponse getUsers(String srchTyp, String srchTxt, Pageable pageable);
}
