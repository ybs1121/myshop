package hello.myshop.biz.products.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.myshop.biz.products.dto.ProductRequest;
import hello.myshop.biz.products.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    ProductService productService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void 품목_저장_호출_테스트() throws Exception {
        //Given
        ProductRequest productRequest = makeProductRequestSample();
        when(productService.saveProduct(productRequest)).thenReturn(1L);
        //When
        //Then
        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(1L));

    }



    private ProductRequest makeProductRequestSample() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setUserId(1L);
        productRequest.setName("TV");
        productRequest.setDescription("엄청난 고화질의 티비");
        productRequest.setStock(100);
        productRequest.setPrice(new BigDecimal("100.00"));
        return productRequest;
    }

}