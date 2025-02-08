package hello.myshop.biz.user.controller;

import hello.myshop.biz.user.dto.UserRequest;
import hello.myshop.biz.user.dto.UserResponse;
import hello.myshop.biz.user.entity.User;
import hello.myshop.biz.user.service.UserService;
import hello.myshop.core.response.ApiResponse;
import hello.myshop.core.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Long>> register(@RequestBody UserRequest user) {
        return ResponseUtil.success(userService.register(user));
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> login(@PathVariable(value = "id") long id) {
        return ResponseUtil.success(userService.getUser(id));
    }

}
