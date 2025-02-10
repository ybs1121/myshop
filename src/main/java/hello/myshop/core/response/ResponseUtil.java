package hello.myshop.core.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    // 성공 응답 생성
    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    // 실패 응답 생성 (커스텀 메시지와 상태 코드)
    public static <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(ApiResponse.fail(message));
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String code, String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(code, message));
    }
}
