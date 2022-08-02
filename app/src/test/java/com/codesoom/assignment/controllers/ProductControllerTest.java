package com.codesoom.assignment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
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
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스의")
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ResultActions create(Map<String, Object> input) throws Exception {
        return mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));
    }

    abstract class Normal {
        public static final String NAME = "고영희";
        public static final String MAKER = "코드숨";
        public static final int PRICE = 2200000;
        public static final String URL = "www.picture.com";

        Map<String, Object> input() {
            HashMap<String, Object> input = new HashMap<>();

            input.put("name", NAME);
            input.put("maker", MAKER);
            input.put("price", PRICE);
            input.put("imageUrl", URL);

            return input;
        }
    }

    @Nested
    @DisplayName("/products 요청은")
    class Describe_create {
        @Nested
        @DisplayName("상품 정보가 주어지면")
        class Context_with_productData extends Normal {
            @Test
            @DisplayName("상품을 리턴한다")
            void It_returns_product() throws Exception {
                create(input())
                        .andExpect(jsonPath("$.name").value(NAME))
                        .andExpect(jsonPath("$.maker").value(MAKER))
                        .andExpect(jsonPath("$.price").value(PRICE))
                        .andExpect(jsonPath("$.imageUrl").value(URL))
                        .andExpect(status().isCreated());
            }
        }

        @Nested
        @DisplayName("상품 정보의 이름이 주어지지 않았다면")
        class Context_without_Name_Maker_Price extends Normal {
            Map<String, Object> prepare() {
                Map<String, Object> inValidInput = new HashMap<>();
                inValidInput.put("name", "");
                inValidInput.put("maker", MAKER);
                inValidInput.put("price", PRICE);
                inValidInput.put("imageUrl", URL);

                return inValidInput;
            }

            void expect(Map<String, Object> inValidInput) throws Exception {
                create(inValidInput)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.[0].fieldName", Is.is("name")))
                        .andExpect(jsonPath("$.[0].message", Is.is("이름은 필수값입니다.")));
            }

            @Test
            @DisplayName("예외 메시지를 응답한다")
            void It_returns_errorResponse() throws Exception {
                Map<String, Object> inValidInput = prepare();
                expect(inValidInput);

                inValidInput.put("name", " ");
                expect(inValidInput);

                inValidInput.put("name", null);
                expect(inValidInput);
            }
        }

        @Nested
        @DisplayName("상품 이름이 길이 제한을 만족하지 않는다면")
        class Context_with_invalidName extends Normal{
            Map<String, Object> prepare() {
                Map<String, Object> inValidInput = new HashMap<>();

                inValidInput.put("name", "원");
                inValidInput.put("maker", MAKER);
                inValidInput.put("price", PRICE);
                inValidInput.put("imageUrl", URL);

                return inValidInput;
            }

            private void expect(Map<String, Object> input) throws Exception {
                create(input)
                        .andExpect(jsonPath("$.[0].fieldName", Is.is("이름의 길이가 범위를 벗어납니다.")))
                        .andExpect(jsonPath("$.[0].message", Is.is("이름의 길이가 범위를 벗어납니다.")))
                        .andExpect(status().isBadRequest());
            }
        }
    }
}
