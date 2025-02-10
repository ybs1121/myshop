package hello.myshop.core.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;
    private String code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>("success", "OK", "", data);
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<T>("fail", "SERVER_ERROR", message, null);
    }

    public static <T> ApiResponse<T> fail(String code, String message) {
        return new ApiResponse<T>("fail", code, message, null);
    }
}
