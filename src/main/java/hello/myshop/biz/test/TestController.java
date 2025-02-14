package hello.myshop.biz.test;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestFactory testFactory;

    @PostMapping("")
    public String test(@RequestBody TestDto testDto) {
        TestDto testDto1 = testFactory.create(testDto);
        return "test";
    }
}
