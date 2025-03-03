package hello.myshop.biz.user.service;

import hello.myshop.biz.user.dto.UserRequest;
import hello.myshop.biz.user.dto.UserResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {

    long register(UserRequest user);

    UserResponse getUser(long id);

    Void updateUser(UserRequest user);

    UserResponse.ListResponse getUsers(String srchTyp, String srchTxt, Pageable pageable);
}
