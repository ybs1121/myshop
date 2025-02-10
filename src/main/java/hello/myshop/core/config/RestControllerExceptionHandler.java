package hello.myshop.core.config;

import hello.myshop.core.response.ApiResponse;
import hello.myshop.core.response.CustomException;
import hello.myshop.core.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class RestControllerExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomException(CustomException ex, Locale locale) {
        log.error("CustomException: {}", ex.getMessage());
        String errorMessage = messageSource.getMessage(ex.getCode(), null, locale);
        return ResponseUtil.error(ex.getCode(), errorMessage);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomException(Exception ex, Locale locale) {
        log.error("error: {}", ex);
        return ResponseUtil.error(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
