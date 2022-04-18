package com.codesoom.assignment.controllers;

import com.codesoom.assignment.AutoConfigureUtf8MockMvc;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureUtf8MockMvc
@DisplayName("ProductController MockMVC 테스트에서")
public class ProductControllerWebTest {
    private static final String PRODUCT_NAME = "상품1";
    private static final String PRODUCT_MAKER = "메이커1";
    private static final Integer PRODUCT_PRICE = 100000;
    private static final String PRODUCT_IMAGE_URL = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F9941A1385B99240D2E";

    private static final String UPDATE_PRODUCT_NAME = "상품1000";
    private static final Integer UPDATE_PRODUCT_PRICE = 100;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    /**
     * 하나의 Product 를 생성해 등록합니다.
     * @return 생성한 Product를 리턴
     */
    private Product createProduct() {
        Product product = Product.builder()
                .name(PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .price(PRODUCT_PRICE)
                .imageUrl(PRODUCT_IMAGE_URL)
                .build();
        return productRepository.save(product);
    }

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("GET - /products 요청시")
    class Describe_of_GET {
        private Product product;

        @BeforeEach
        void setUp() {
            product = createProduct();
        }

        @Nested
        @DisplayName("Product 가 있을 경우")
        class Content_with_product_list {
            final List<Product> products = new ArrayList<>();
            @BeforeEach
            void setUp() {
                products.add(product);
            }

            @Test
            @DisplayName("모든 Product를 보여준다")
            void it_returns_product_list() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content()
                                .string(StringContains.containsString(
                                        objectMapper.writeValueAsString(products))));
            }
        }

        @Nested
        @DisplayName("Product가 없을 경우")
        class Content_with_empty_list {
            @BeforeEach
            void setUp() {
                productRepository.deleteAll();
            }

            @Test
            @DisplayName("빈 배열을 보여준다")
            void it_returns_empty_list() throws Exception {
                mockMvc.perform(get("/products")
                                .contentType(APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
                        .andExpect(content()
                                .string(StringContains.containsString("[]")))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("GET - /products/{id} 요청시")
    class Describe_of_detail_product {
        private Product product;

        @BeforeEach
        void setUp() {
            product = createProduct();
        }

        @Nested
        @DisplayName("{id} 와 동일한 Product가 있을 경우")
        class Context_with_product {
            private long productId;

            @BeforeEach
            void setUp() {
                productId = product.getId();
            }

            @Test
            @DisplayName("{id}와 동일한 Product를 보여준다")
            void it_return_product() throws Exception {
                mockMvc.perform(get("/products/" + productId))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("price").exists())
                        .andExpect(jsonPath("imageUrl").exists());
            }
        }

        @Nested
        @DisplayName("{id}와 동일한 Product 가 없을 경우")
        class Context_without_product {
            private long productId;

            @BeforeEach
            void setUp() {
                productId = product.getId();
                productRepository.delete(product);
            }

            @Test
            @DisplayName("404 에러를 던진다")
            void it_throw_productNotFoundException() throws Exception {
                mockMvc.perform(get("/products/{id}", productId))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("POST - /products 요청시")
    class Describe_of_create_product {

        @Nested
        @DisplayName("생성에 필요한 데이터가 주어진다면")
        class Context_with_valid_body {
            private final ProductData productData = ProductData
                    .builder()
                    .name(PRODUCT_NAME)
                    .maker(PRODUCT_MAKER)
                    .price(PRODUCT_PRICE)
                    .imageUrl(PRODUCT_IMAGE_URL)
                    .build();

            @Test
            @DisplayName("상품을 등록하고 등록한 상품을 포함하여 응답한다")
            void it_save_and_return_product() throws Exception {
                mockMvc.perform(post("/products")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productData)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("price").exists())
                        .andExpect(jsonPath("imageUrl").exists());
            }
        }
    }

    @Nested
    @DisplayName("PATCH - /products/{id} 요청시")
    class Describe_of_patch {
        private Product product;

        @BeforeEach
        void setUp() {
            product = createProduct();
        }

        @Nested
        @DisplayName("{id}와 동일한 제품이 있을 경우")
        class Context_with_valid_id {
            private Long productId;
            private final ProductData productData = ProductData
                    .builder()
                    .name(UPDATE_PRODUCT_NAME)
                    .maker(PRODUCT_MAKER)
                    .price(UPDATE_PRODUCT_PRICE)
                    .imageUrl(PRODUCT_IMAGE_URL)
                    .build();

            @BeforeEach
            void setUp() {
                productId = product.getId();
            }

            @Test
            @DisplayName("제품을 수정하고 수정된 제품을 포함하여 응답한다")
            void it_return_updated_product() throws Exception {
                mockMvc.perform(patch("/products/{id}", productId)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productData)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("price").exists())
                        .andExpect(jsonPath("imageUrl").exists());
            }
        }

        @Nested
        @DisplayName("{id}와 동일한 제품이 없을 경우")
        class Context_with_invalid_id {
            private Long productId;
            private final ProductData productData = ProductData
                    .builder()
                    .name(PRODUCT_NAME)
                    .maker(PRODUCT_MAKER)
                    .price(PRODUCT_PRICE)
                    .build();

            @BeforeEach
            void setUp() {
                productId = product.getId();
                productRepository.deleteById(productId);
            }

            @Test
            @DisplayName("404 에러를 던진다")
            void it_throw_not_found() throws Exception {
                mockMvc.perform(patch("/products/{id}", productId)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productData)))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("DELETE - /products/{id} 요청시")
    class Describe_of_delete_product {
        private Product product;

        @BeforeEach
        void setUp() {
            product = createProduct();
        }

        @Nested
        @DisplayName("{id}와 동일한 제품이 있을 경우")
        class Context_with_valid_id {
            private Long productId;

            @BeforeEach
            void setUp() {
                productId = product.getId();
            }

            @Test
            @DisplayName("제품을 삭제후 상태코드 204로 응답한다")
            void it_delete_product_by_id() throws Exception {
                mockMvc.perform(delete("/products/{id}", productId))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("{id}와 동일한 제품이 없을 경우")
        class Context_with_invalid_id {
            private Long productId;

            @BeforeEach
            void setUp() {
                productId = product.getId();
                productRepository.deleteById(productId);
            }

            @Test
            @DisplayName("상태코드 404로 응답한다")
            void it_throw_not_found() throws Exception {
                mockMvc.perform(delete("/products/{id}", productId))
                        .andExpect(status().isNotFound());
            }
        }
    }
}