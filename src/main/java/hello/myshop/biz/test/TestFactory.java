package hello.myshop.biz.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class TestFactory {

    private final ObjectMapper objectMapper;
    private final Validator validator;

    public TestDto create(TestDto request) {
        return createAndValidate(request, TestDto.class);
    }

    private <T extends TestDto> T createAndValidate(TestDto request, Class<T> targetClass) {
        T scrapRequest = objectMapper.convertValue(request, targetClass);
        validate(scrapRequest);
        return scrapRequest;
    }

    private <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
