package com.codesoom.assignment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스의")
public class ProductControllerTest {
    public static final String GIVEN_NAME = "고영희";
    public static final String GIVEN_MAKER = "코드숨";
    public static final int GIVEN_PRICE = 2200000;
    public static final String GIVEN_URL = "www.picture.com";

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ResultActions create() throws Exception {
        HashMap<String, Object> input = new HashMap<>();
        input.put("name", GIVEN_NAME);
        input.put("maker", GIVEN_MAKER);
        input.put("price", GIVEN_PRICE);
        input.put("imageUrl", GIVEN_URL);

        return mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));
    }

    @Nested
    @DisplayName("/products 요청은")
    class Describe_create {
        @Nested
        @DisplayName("상품 정보가 주어지면")
        class Context_with_productData {
            @Test
            @DisplayName("상품을 리턴한다")
            void It_returns_product() throws Exception {
                create().andExpect(jsonPath("$.name").value(GIVEN_NAME))
                        .andExpect(jsonPath("$.maker").value(GIVEN_MAKER))
                        .andExpect(jsonPath("$.price").value(GIVEN_PRICE))
                        .andExpect(jsonPath("$.imageUrl").value(GIVEN_URL));
            }
        }
    }
}
