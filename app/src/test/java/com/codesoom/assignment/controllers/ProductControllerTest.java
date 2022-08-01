package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.infra.JpaProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스의")
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    @DisplayName("/products 요청은")
    class Describe_create {
        @Nested
        @DisplayName("상품 정보가 주어지면")
        class Context_with_productData {
            @Test
            @DisplayName("상품을 리턴한다")
            void It_returns_product() throws Exception {
                HashMap<String, Object> input = new HashMap<>();
                input.put("name", "고영희");
                input.put("maker", "코드숨");
                input.put("price", 2200000);
                input.put("imageUrl", "www.picture.com");

                mockMvc.perform(post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input)))
                        .andExpect(jsonPath("$.name").value("고영희"))
                        .andExpect(jsonPath("$.maker").value("코드숨"))
                        .andExpect(jsonPath("$.price").value(2200000))
                        .andExpect(jsonPath("$.imageUrl").value("www.picture.com"));
            }
        }
    }
}
