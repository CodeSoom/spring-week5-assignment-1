package com.codesoom.assignment.controllers;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @MockBean
    private ProductService productService;

    private static final String PRODUCT_NAME = "춘식이 고구마 장난감";
    private static final String MAKER        = "카카오";
    private static final Integer PRICE       = 10000;
    private static final String IMAGE_URL    = "http://localhost:8080/choonsik";

    private static final Boolean FORCE_ENCODING = true;

    @BeforeEach
    void setUp() {
        //MediaType.APPLICATION_JSON_UTF8 Deprecated로 인한 UTF-8 필터 추가
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", FORCE_ENCODING))
                .build();

        Product product = Product.builder()
                .id(1L)
                .name(PRODUCT_NAME)
                .maker(MAKER)
                .price(PRICE)
                .build();

        given(productService.getProducts()).willReturn(List.of(product));

        given(productService.getProduct(1L)).willReturn(product);

//        given(productService.getProduct(1000L)).willThrow(new ProductNotFoundException(1000L));

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

//        given(productService.deleteProduct(1000L)).willThrow(new ProductNotFoundException(1000L));
    }

    @Nested
    @DisplayName("GET 요청은")
    class Describe_get {
        @Nested
        @DisplayName("id가 없으면")
        class Context_without_segment {
            @Test
            @DisplayName("모든 Product 리스트를 리턴한다")
            void it_return_products() throws Exception {
                mockMvc.perform(get("/products")
                                .accept(APPLICATION_JSON))
                        .andExpect(status().isOk());

                verify(productService).getProducts();
            }
        }

        @Nested
        @DisplayName("존재하는 id를 넘겨받으면")
        class Context_with_existedId {
            @Test
            @DisplayName("해당하는 id의 Product를 리턴한다")
            void it_return_product() throws Exception {
                mockMvc.perform(get("/products/1"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(PRODUCT_NAME)));

                verify(productService).getProduct(1L);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id를 넘겨받으면")
        class Context_with_notExistedId {
            @BeforeEach
            void setUp() {
                given(productService.getProduct(1000L))
                        .willThrow(new ProductNotFoundException(1000L));
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throw_ProductNotFoundException() throws Exception {
                mockMvc.perform(get("/products/1000"))
                        .andExpect(status().isNotFound());

                verify(productService).getProduct(1000L);
            }
        }
    }

    @Nested
    @DisplayName("POST 요청은")
    class Describe_post {
        @Nested
        @DisplayName("Valid 통과한 ProductData가 오면")
        class Context_with_validProductData {
            @Test
            @DisplayName("상품을 새로 만들어 응답한다")
            void it_return_new_product() throws Exception {
                ProductData product = ProductData.builder()
                        .name(PRODUCT_NAME)
                        .maker(MAKER)
                        .price(PRICE)
                        .imageUrl(IMAGE_URL)
                        .build();
                String content = new ObjectMapper().writeValueAsString(product);

                mockMvc.perform(post("/products")
                                .content(content)
                                .contentType(APPLICATION_JSON))
                        .andExpect(status().isCreated());

                verify(productService).createProduct(any(ProductData.class));
            }
        }

        @Nested
        @DisplayName("Valid 통과하지 못한 ProductData가 오면")
        class Context_with_notValidProductData {
            @Test
            @DisplayName("에러를 던진다")
            void it_throw_exception() throws Exception {
                ProductData product = ProductData.builder()
                        .name("")
                        .maker("")
                        .price(PRICE)
                        .imageUrl(IMAGE_URL)
                        .build();
                String content = new ObjectMapper().writeValueAsString(product);

                mockMvc.perform(post("/products")
                                .content(content)
                                .contentType(APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("필수 입력 사항입니다.")));
            }
        }
    }

    @Nested
    @DisplayName("PATCH 요청은")
    class Describe_patch {
        @Nested
        @DisplayName("파라미터로 존재하는 id가 넘어오면")
        class Context_with_validId {
            @Test
            @DisplayName("해당하는 id의 product를 업데이트한다")
            void it_update_product() throws Exception {
                ProductData product = ProductData.builder()
                                                    .name(PRODUCT_NAME + "!!!")
                                                    .maker(MAKER)
                                                    .price(PRICE)
                                                    .build();

                String content = new ObjectMapper().writeValueAsString(product);

                mockMvc.perform(patch("/products/1")
                                .content(content)
                                .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk());

                verify(productService).updateProduct(eq(1L), any(ProductData.class));

                assertThat(product.getName())
                        .isEqualTo(PRODUCT_NAME + "!!!");
            }
        }

        @Nested
        @DisplayName("파라미터로 존재하지 않는 id가 넘어오면")
        class Context_with_invalidId {
            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throw_ProductNotFoundException() throws Exception {
                ProductData product = ProductData.builder()
                        .name(PRODUCT_NAME + "!!!")
                        .maker(MAKER)
                        .price(PRICE)
                        .build();

                String content = new ObjectMapper().writeValueAsString(product);

                mockMvc.perform(patch("/products/1000")
                                .content(content)
                                .contentType(APPLICATION_JSON))
                        .andExpect(status().isNotFound());

                verify(productService).updateProduct(eq(1000L), any(ProductData.class));
            }
        }

        @Nested
        @DisplayName("Valid에 통과하지 못한 ProductData가 넘어오면")
        class Context_with_notValidProductData {
            @Test
            @DisplayName("에러를 던진다")
            void it_throw_exception() throws Exception {
                ProductData product = ProductData.builder()
                        .name("")
                        .maker("")
                        .price(PRICE)
                        .build();

                String content = new ObjectMapper().writeValueAsString(product);

                mockMvc.perform(patch("/products/1")
                                .content(content)
                                .contentType(APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("DELETE 요청은")
    class Describe_delete {
        @Nested
        @DisplayName("파라미터로 존재하는 id가 넘어오면")
        class Context_with_validId {
            @Test
            @DisplayName("해당하는 id의 Product를 삭제한다")
            void it_remove_product() throws Exception {
                mockMvc.perform(delete("/products/1"))
                        .andExpect(status().isNoContent());

                verify(productService).deleteProduct(1L);
            }
        }

        @Nested
        @DisplayName("파라미터로 존재하지 않는 id가 넘어오면")
        class Context_with_invalidId {
            @BeforeEach
            void setUp() {
                given(productService.deleteProduct(1000L))
                        .willThrow(new ProductNotFoundException(1000L));
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_return_ProductNotFoundException() throws Exception {
                mockMvc.perform(delete("/products/1000"))
                        .andExpect(status().isNotFound());

                verify(productService).deleteProduct(1000L);
            }
        }
    }
}
