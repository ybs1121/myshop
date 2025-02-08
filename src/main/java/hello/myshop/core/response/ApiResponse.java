package hello.myshop.core.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>("success", 200, "", data);
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<T>("fail", 500, message, null);
    }
}
