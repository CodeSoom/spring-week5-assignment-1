package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        Product product = Product.builder()
                .id(1L)
                .name("쥐돌이")
                .maker("냥이월드")
                .price(5000)
                .build();

        given(productService.getProducts()).willReturn(List.of(product));

        given(productService.getProduct(1L)).willReturn(product);

        given(productService.getProduct(1000L))
                .willThrow(new ProductNotFoundException(1000L));

        given(productService.createProduct(any(ProductData.class)))
                .willReturn(product);

        given(productService.updateProduct(eq(1L), any(ProductData.class)))
                .will(invocation -> {
                    Long id = invocation.getArgument(0);
                    ProductData productData = invocation.getArgument(1);
                    return Product.builder()
                            .id(id)
                            .name(productData.getName())
                            .maker(productData.getMaker())
                            .price(productData.getPrice())
                            .build();
                });

        given(productService.updateProduct(eq(1000L), any(ProductData.class)))
                .willThrow(new ProductNotFoundException(1000L));

        given(productService.deleteProduct(1000L))
                .willThrow(new ProductNotFoundException(1000L));
    }
    private final ObjectMapper mapper = new ObjectMapper();

    private ResultActions performPost(String url, Object object) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(object)));
    }

    private ResultActions performGet(String url) throws Exception {
        return mockMvc.perform(get(url));
    }

    private ResultActions performDelete(String url) throws Exception {
        return mockMvc.perform(delete(url));
    }

    private ResultActions performPut(String url, Object object) throws Exception {
        return mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(object)));
    }

    private ResultActions performPatch(String url, Object object) throws Exception {
        return mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(object)));
    }

    @Nested
    @DisplayName("GET /tasks API는")
    class Describe_GET_tasks {
        // 검사할 인자가 없음
    }

    @Nested
    @DisplayName("GET /tasks/{id} API는")
    class Describe_GET_tasks_id {
        @Nested
        @DisplayName("Path variable로 숫자값이 전달되지 않으면")
        class Context_id_not_number {

            @Test
            @DisplayName("MethodArgumentTypeMismatchException 예외를 발생시킨다")
            void it_throws_exception() throws Exception {
                performGet("/tasks/abc")
                        .andExpect(status().isBadRequest());
            }
        }
    }
}
