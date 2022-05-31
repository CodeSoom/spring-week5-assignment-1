package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@DisplayName("ProductController Web Test")
class ProductControllerTest {

    // TODO 구현 목록
    // 1. GET /products
    //  - 장난감 목록이 비어있는 경우 -> 200 ok
    //  - 장난감 목록이 있는 경우 -> 200 ok
    // 2. GET /products/{id}
    //  - id가 유효한 경우 -> 200 ok
    //  - id가 유효하지 않은 경우 -> 404 Not Found
    // 3. POST /products
    //  - 입력한 정보가 유효한 경우 -> 201 Created
    // 4. PATCH /products/{id}
    //  - id가 유효한 경우 -> 200 ok
    //  - id가 유효하지 않은 경우 -> 404 Not Found
    // 5. PUT /products/{id}
    //  - id가 유효한 경우 -> 200 ok
    //  - id가 유효하지 않은 경우 -> 404 Not Found
    // 6. DELETE /products/{id}
    //  - id가 유효한 경우 -> No Content
    //  - id가 유효하지 않은 경우 -> 404 Not Found

    @Autowired
    MockMvc mockMvc;

    @Nested
    @DisplayName("GET /products")
    class list {

        @Nested
        @DisplayName("장난감 목록이 비어있다면")
        class when_product_list_is_not_empty {

            @Test
            @DisplayName("200 ok를 반환한다.")
            void list() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk());
            }
        }
    }

}