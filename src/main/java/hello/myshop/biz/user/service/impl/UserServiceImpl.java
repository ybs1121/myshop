package hello.myshop.biz.user.service.impl;

import hello.myshop.biz.user.common.mapper.UserConvertor;
import hello.myshop.biz.user.dto.UserRequest;
import hello.myshop.biz.user.dto.UserResponse;
import hello.myshop.biz.user.entity.User;
import hello.myshop.biz.user.repository.jpa.UserJpaRepository;
import hello.myshop.biz.user.service.UserService;
import hello.myshop.core.response.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final UserConvertor userConvertor;

    @Override
    public long register(UserRequest userRequest) {
        User user = userJpaRepository.save(userConvertor.toEntity(userRequest));
        return user.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getUser(long id) {
        return userJpaRepository.findById(id).map(user -> {
            return userConvertor.toDto(user);
        }).orElseThrow(() -> new CustomException("USER0001"));

    }

    @Override
    public Void updateUser(UserRequest userRequest) {
        User user = userJpaRepository.findById(userRequest.getId()).orElseThrow(
                () -> new CustomException("USER0001")
        );
        user.update(userRequest);
        return null;
    }
}
