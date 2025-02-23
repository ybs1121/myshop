package hello.myshop.biz.user.hepler;

import hello.myshop.biz.user.entity.User;
import hello.myshop.biz.user.repository.jpa.UserJpaRepository;
import hello.myshop.core.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserHelper {
    private final UserJpaRepository userJpaRepository;


    public void validateUserExist(Long userId) {
        if (!userJpaRepository.existsById(userId)) {
            throw new CustomException("USER0001");
        }
    }

    public User getUserReferenceById(Long userId) {
        return userJpaRepository.getReferenceById(userId);
    }


}
