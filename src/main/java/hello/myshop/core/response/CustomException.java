package hello.myshop.core.response;


import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final String code; // 에러 코드
    private String errorMsg; // 에러 메시지

    public CustomException(String code, String errorMsg) {
        super(errorMsg); // RuntimeException의 메시지 필드에 errorMsg 저장
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public CustomException(String code) {
        this.code = code;
    }

}
