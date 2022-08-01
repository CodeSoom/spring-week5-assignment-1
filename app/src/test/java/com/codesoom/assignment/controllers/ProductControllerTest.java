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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스의")
public class ProductControllerTest {
    public static final String NORMAL_NAME = "고영희";
    public static final String NORMAL_MAKER = "코드숨";
    public static final int NORMAL_PRICE = 2200000;
    public static final String NORMAL_URL = "www.picture.com";

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, Object> normalInput() {
        HashMap<String, Object> input = new HashMap<>();
        input.put("name", NORMAL_NAME);
        input.put("maker", NORMAL_MAKER);
        input.put("price", NORMAL_PRICE);
        input.put("imageUrl", NORMAL_URL);
        return input;
    }

    private ResultActions create(Map<String, Object> input) throws Exception {
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
                create(normalInput())
                        .andExpect(jsonPath("$.name").value(NORMAL_NAME))
                        .andExpect(jsonPath("$.maker").value(NORMAL_MAKER))
                        .andExpect(jsonPath("$.price").value(NORMAL_PRICE))
                        .andExpect(jsonPath("$.imageUrl").value(NORMAL_URL))
                        .andExpect(status().isCreated());
            }
        }

        @Nested
        @DisplayName("상품 정보의 이름이 주어지지 않았다면")
        class Context_without_Name_Maker_Price {
            Map<String, Object> prepare() {
                Map<String, Object> inValidInput = new HashMap<>();
                inValidInput.put("name", "");
                inValidInput.put("maker", NORMAL_MAKER);
                inValidInput.put("price", NORMAL_PRICE);
                inValidInput.put("imageUrl", NORMAL_URL);

                return inValidInput;
            }

            void expect(Map<String, Object> inValidInput ) throws Exception {
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
    }
}
