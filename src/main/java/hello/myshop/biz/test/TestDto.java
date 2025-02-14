package hello.myshop.biz.test;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class TestDto {

    @NotEmpty
    private String str;

    @Size(min = 1)
    private List<String> list1;

    private List<String> list2;

    private int num;

}
