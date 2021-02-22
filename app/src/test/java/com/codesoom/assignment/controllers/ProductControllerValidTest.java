package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ProductController 클래스")
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerValidTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("GET /products/{id} 리퀘스트는")
    @Nested
    class Describe_GET_product_id {
        @DisplayName("주어진 id가 정수가 아니라면")
        class Context_Not_Integer {
            @DisplayName("404 코드를 응답한다")
            @Test
            void it_returns_404() throws Exception {
                String invalidId = "Invalid Value";

                mockMvc.perform(
                        get("/products/{id}", invalidId)
                )
                        .andExpect(status().isNotFound());
            }
        }
    }

}
