package com.codesoom.assignment.controllers;

import com.codesoom.assignment.BadRequestException;
import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    private static final Long ID = 1L;
    private static final Long INVALID_ID = 1000L;
    private static final String NAME = "쥐돌이";
    private static final String INVALID_NAME = "";
    private static final String NEW_NAME = "쥐순이";
    private static final String MAKER = "냥이월드";
    private static final int PRICE = 5000;
    private static final String IMAGE_URL = "http://image.kyobobook.co.kr/newimages/giftshop_new/goods/400/1095/hot1602809707085.jpg";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product product;
    private ProductData productData;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(ID)
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .imageUrl(IMAGE_URL)
                .build();

        productData = ProductData.builder()
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .imageUrl(IMAGE_URL)
                .build();
    }

    @Nested
    @DisplayName("GET /products 요청 시")
    class Describe_get_products {
        @Nested
        @DisplayName("만약 1개의 product가 저장된 경우")
        class Context_if_one_product_stored {
            @BeforeEach
            void setUp() {
                given(productService.getProducts()).willReturn(List.of(product));
            }

            @Nested
            @DisplayName("1개의 Product가 저장되어있는 리스트를 반환한다")
            class It_returns_list_contains_one_proudct {
                ResultActions subject() throws Exception {
                    return mockMvc.perform(get("/products"));
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isOk())
                            .andExpect(jsonPath("$[0].id").value(ID))
                            .andExpect(jsonPath("$[0].name").value(NAME))
                            .andExpect(jsonPath("$[0].maker").value(MAKER))
                            .andExpect(jsonPath("$[0].price").value(PRICE))
                            .andExpect(jsonPath("$[0].imageUrl").value(IMAGE_URL));
                }
            }
        }
    }

    @Nested
    @DisplayName("GET /products/{id} 요청 시")
    class Describe_get_products_by_id {
        @Nested
        @DisplayName("만약 유효한 id가 주어진 경우")
        class Context_if_valid_id_given {
            @BeforeEach
            void setUp() {
                given(productService.getProduct(ID)).willReturn(product);
            }

            @Nested
            @DisplayName("status code 200을 응답한다")
            class It_response_status_code_200{
                ResultActions subject() throws Exception {
                    return mockMvc.perform(get("/products/{id}", ID));
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isOk())
                            .andExpect(jsonPath("$.id").value(ID))
                            .andExpect(jsonPath("$.name").value(NAME))
                            .andExpect(jsonPath("$.maker").value(MAKER))
                            .andExpect(jsonPath("$.price").value(PRICE))
                            .andExpect(jsonPath("$.imageUrl").value(IMAGE_URL));
                }
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 id가 주어진 경우")
        class Context_if_invalid_id_given {
            @BeforeEach
            void setUp() {
                given(productService.getProduct(INVALID_ID)).willThrow(new ProductNotFoundException(INVALID_ID));
            }

            @Nested
            @DisplayName("status code 404를 응답한다")
            class It_response_status_code_404 {
                ResultActions subject() throws Exception {
                    return mockMvc.perform(get("/products/{id}", INVALID_ID));
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isNotFound());
                }
            }
        }
   }

   @Nested
   @DisplayName("POST /products 요청 시")
   class Describe_post_products {
       @Nested
       @DisplayName("유효한 속성을 가진 product가 주어졌을 경우")
       class Context_if_product_with_valid_attributes_given {
           @BeforeEach
           void setUp() {
               given(productService.createProduct(any(ProductData.class))).willReturn(product);
           }

           @Nested
           @DisplayName("status code 201을 응답한다")
           class It_response_status_code_201 {
               ResultActions subject() throws Exception {
                   ObjectMapper objectMapper = new ObjectMapper();
                   String jsonProductData = objectMapper.writeValueAsString(productData);

                   return mockMvc.perform(post("/products")
                           .contentType(MediaType.APPLICATION_JSON)
                                   .content(jsonProductData)
                   );
               }

               @Test
               void test() throws Exception {
                   subject().andExpect(status().isCreated())
                           .andExpect(jsonPath("$.name").value(NAME))
                           .andExpect(jsonPath("$.maker").value(MAKER))
                           .andExpect(jsonPath("$.price").value(PRICE))
                           .andExpect(jsonPath("$.imageUrl").value(IMAGE_URL));

                   verify(productService).createProduct(any(ProductData.class));
               }
           }
       }

       @Nested
       @DisplayName("유효하지 않은 속성을 가진 product가 주어졌을 경우")
       class Context_if_product_with_invalid_attributes_given {
           @BeforeEach
           void setUp() {
               productData = ProductData.builder()
                       .name(INVALID_NAME)
                       .maker(MAKER)
                       .price(PRICE)
                       .imageUrl(IMAGE_URL)
                       .build();

               given(productService.createProduct(eq(productData))).willThrow(new BadRequestException());
           }

           @Nested
           @DisplayName("status code 400을 응답한다")
           class It_response_status_code_400 {
               ResultActions subject() throws Exception {
                   ObjectMapper objectMapper = new ObjectMapper();
                   String jsonProductData = objectMapper.writeValueAsString(productData);

                   return mockMvc.perform(post("/products")
                           .contentType(MediaType.APPLICATION_JSON)
                           .content(jsonProductData)
                   );
               }

               @Test
               void test() throws Exception {
                   subject().andExpect(status().isBadRequest());
               }
           }
       }
   }

    @Nested
    @DisplayName("PATCH /products 요청 시")
    class Describe_patch_products {
        @BeforeEach
        void setUp() {
            productData = ProductData.builder()
                    .name(NEW_NAME)
                    .maker(MAKER)
                    .price(PRICE)
                    .imageUrl(IMAGE_URL)
                    .build();
        }

        @Nested
        @DisplayName("유효한 id가 주어졌을 경우")
        class Context_if_valid_id_given {
            @BeforeEach
            void setUp() {
                given(productService.updateProduct(eq(ID), any(ProductData.class)))
                        .will(invocation -> {
                            Long id = invocation.getArgument(0);
                            ProductData productData = invocation.getArgument(1);
                            return Product.builder()
                                    .id(id)
                                    .name(productData.getName())
                                    .maker(productData.getMaker())
                                    .price(productData.getPrice())
                                    .imageUrl(productData.getImageUrl())
                                    .build();
                        });
            }

            @Nested
            @DisplayName("status code 200을 응답한다")
            class It_response_status_code_200{
                ResultActions subject() throws Exception {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonProductData = objectMapper.writeValueAsString(productData);

                    return mockMvc.perform(patch("/products/{id}", ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonProductData)
                    );
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isOk())
                            .andExpect(jsonPath("$.name").value(NEW_NAME));
                }
            }
        }

        @Nested
        @DisplayName("유효하지 않은 id가 주어졌을 경우")
        class Context_if_invalid_id_given {
            @BeforeEach
            void setUp() {
                given(productService.updateProduct(eq(INVALID_ID), any(ProductData.class))).willThrow(new ProductNotFoundException(INVALID_ID));
            }

            @Nested
            @DisplayName("status code 404를 응답한다")
            class It_response_status_code_404 {
                ResultActions subject() throws Exception {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonProductData = objectMapper.writeValueAsString(productData);

                    return mockMvc.perform(patch("/products/{id}", INVALID_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonProductData)
                    );
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isNotFound());
                }
            }
        }

        @Nested
        @DisplayName("유효하지 않은 속성을 가진 product가 주어졌을 경우")
        class Context_if_product_with_invalid_attributes_given {
            @BeforeEach
            void setUp() {
                productData = ProductData.builder()
                        .name(INVALID_NAME)
                        .maker(MAKER)
                        .price(PRICE)
                        .imageUrl(IMAGE_URL)
                        .build();

                given(productService.updateProduct(eq(ID), eq(productData))).willThrow(new BadRequestException());
            }

            @Nested
            @DisplayName("status code 400을 응답한다")
            class It_response_status_code_400 {
                ResultActions subject() throws Exception {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonProductData = objectMapper.writeValueAsString(productData);

                    return mockMvc.perform(patch("/products/{id}", ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonProductData)
                    );
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isBadRequest());
                }
            }
        }
    }

    @Nested
    @DisplayName("DELETE /products 요청 시")
    class Describe_delete_products {
        @Nested
        @DisplayName("유효한 id가 주어졌을 경우")
        class Context_if_valid_id_given {
            @Nested
            @DisplayName("status code 204를 응답한다")
            class It_response_status_code_204{
                ResultActions subject() throws Exception {
                    return mockMvc.perform(delete("/products/{id}", ID));
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isNoContent());

                    verify(productService).deleteProduct(ID);
                }
            }
        }

        @Nested
        @DisplayName("유효하지 않은 id가 주어졌을 경우")
        class Context_if_invalid_id_given {
            @BeforeEach
            void setUp() {
                given(productService.deleteProduct(INVALID_ID)).willThrow(new ProductNotFoundException(INVALID_ID));
            }

            @Nested
            @DisplayName("status code 404를 응답한다")
            class It_response_status_code_404 {
                ResultActions subject() throws Exception {
                    return mockMvc.perform(delete("/products/{id}", INVALID_ID));
                }

                @Test
                void test() throws Exception {
                    subject().andExpect(status().isNotFound());
                }
            }
        }
    }
}
