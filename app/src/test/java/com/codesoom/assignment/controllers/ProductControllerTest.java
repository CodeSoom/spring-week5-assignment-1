package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductRegisterService;
import com.codesoom.assignment.application.ProductSearchService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@DisplayName("ProductController Web Test")
class ProductControllerTest {

    // TODO 구현 목록
    // 1. GET /products
    //  - 장난감 목록이 비어있는 경우 -> 200 ok -> 완료
    //  - 장난감 목록이 있는 경우 -> 200 ok -> 완료
    // 2. GET /products/{id}
    //  - id가 유효한 경우 -> 200 ok -> 완료
    //  - id가 유효하지 않은 경우 -> 404 Not Found -> 완료
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

    @MockBean
    ProductSearchService productSearchService;

    @MockBean
    ProductRegisterService productRegisterService;

    private final Long ID = 1L;
    private final Long INVALID_ID = 1000L;
    private final String NAME = "캣타워";
    private final String MAKER = "(주)애옹이네";
    private final BigDecimal PRICE = BigDecimal.valueOf(100000);
    private final String CONTENT = "{\"name\" : \"캣 타워\", \"maker\" : \"(주)애옹이네\", \"price\" : 1000}";
    private final String INVALID_CONTENT = "{\"maker\" : \"(주)애옹이네\", \"price\" : 1000}";

    @BeforeEach
    void setUp() {
        Product product = Product.builder()
                .id(ID)
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .build();

        setUpSearch(product);
        setUpRegister(product);
    }

    void setUpSearch(Product product) {
        // 전체 조회
        given(productSearchService.findProducts())
                .willReturn(List.of(product));

        // 단건 조회
        given(productSearchService.findProduct(eq(INVALID_ID)))
                .willThrow(ProductNotFoundException.class);

        given(productSearchService.findProduct(eq(ID)))
                .willReturn(product);
    }

    void setUpRegister(Product product) {
        given(productRegisterService.register(any(Product.class)))
                .willReturn(product);
    }

    @Nested
    @DisplayName("GET /products")
    class list {

        @Nested
        @DisplayName("장난감 목록이 비어있다면")
        class when_product_list_is_empty {

            @Test
            @DisplayName("200 ok로 응답한다.")
            void list_200_ok() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("장난감 목록이 비어있지 않다면")
        class when_product_list_is_not_empty {

            @Test
            @DisplayName("200 ok로 응답한다.")
            void list_200_ok() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("장난감 목록을 반환한다.")
            void list() throws Exception {
                mockMvc.perform(
                        get("/products")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                        )
                        .andExpect(content().string(containsString("캣타워")));
            }
        }

    }

    @Nested
    @DisplayName("GET /products/{id}")
    class detail {

        @Nested
        @DisplayName("입력한 id가 존재한다면")
        class when_id_is_exist {

            @Test
            @DisplayName("200 ok로 응답한다.")
            void detail_200_ok() throws Exception {
                mockMvc.perform(get("/products/" + ID))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("장난감 정보를 반환한다.")
            void detail() throws Exception {
                mockMvc.perform(
                        get("/products/" + ID)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                        )
                        .andExpect(content().string(containsString(NAME)));
            }
        }

        @Nested
        @DisplayName("입력한 id가 존재하지 않는다면")
        class when_id_is_not_exist {

            @Test
            @DisplayName("404 Not Found로 응답한다.")
            void detail_404_not_found() throws Exception {
                mockMvc.perform(get("/products/" + INVALID_ID))
                        .andExpect(status().isNotFound());
            }
        }

    }

    @Nested
    @DisplayName("POST /products")
    class register {

        @Nested
        @DisplayName("이름, 제조사, 가격을 입력하였다면")
        class when_register_with_valid_data {

            @Test
            @DisplayName("201 Created로 응답한다.")
            void register_201_ok() throws Exception {
                mockMvc.perform(
                                post("/products")
                                        .accept(MediaType.APPLICATION_JSON_UTF8)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(CONTENT)
                        )
                        .andExpect(status().isCreated());
            }

            @Test
            @DisplayName("등록된 장난감 정보를 반환한다.")
            void register() throws Exception {
                mockMvc.perform(
                                post("/products")
                                        .accept(MediaType.APPLICATION_JSON_UTF8)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(CONTENT)
                        )
                        .andExpect(content().string(containsString(NAME)));
            }
        }

        @Nested
        @DisplayName("이름, 제조사, 가격을 입력하지 않았다면")
        class when_register_with_invalid_data {

            @Test
            @DisplayName("400 Bad Request로 응답한다.")
            void register_400_bad_request() throws Exception {
                mockMvc.perform(
                                post("/products")
                                        .accept(MediaType.APPLICATION_JSON_UTF8)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(INVALID_CONTENT)
                        )
                        .andExpect(status().isBadRequest());
            }
        }

    }
}