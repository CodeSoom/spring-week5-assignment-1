package com.codesoom.assignment.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.codesoom.assignment.domain.ProductConstants.PRODUCT_DATA;
import static com.codesoom.assignment.domain.ProductConstants.EMPTY_LIST;

import com.codesoom.assignment.controllers.ProductController;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.utils.Parser;
import com.google.common.collect.Lists;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("Product 리소스")
@WebMvcTest(ProductController.class)
public class ProductWebTest {
    @Autowired
    private MockMvc mockMvc;

    ResultActions subjectGetProduct() throws Exception {
        return mockMvc.perform(
            get("/products")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );
    }

    ResultActions subjectPostProduct(final String input) throws Exception {
        return mockMvc.perform(
            post("/products")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON)
                // TODO requestBOdy로 변경해야함
                .content(input)
        );
    }

    ResultActions subjectDeleteProduct(final Long id) throws Exception {
        return mockMvc.perform(
            delete("/products" + id)
        );
    }

    // TODO @Nested 추가해야함
    @DisplayName("전체 목록 조회 엔드포인트는")
    class Describe_get_products {
        private String content;

        @Nested
        @DisplayName("저장된 Product가 없다면")
        class Context_product_empty {
            @BeforeEach
            void beforeEach() throws Exception {
                content = Parser.toJson(EMPTY_LIST);
            }

            @Test
            @DisplayName("빈 목록을 리턴한다.")
            void it_returns_a_empty_list() throws Exception {
                subjectGetProduct()
                    .andExpect(status().isOk())
                    .andExpect(content().string(content));
            }
        }

        @Nested
        @DisplayName("저장된 Product가 있다면")
        class Context_product_exist {
            private Product product;

            @BeforeEach
            void beforeEach() throws Exception {
                product = Parser.toObject(
                    subjectPostProduct(Parser.toJson(PRODUCT_DATA))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), Product.class
                );

                content = Parser.toJson(Lists.newArrayList(product));
            }

            @AfterEach
            void afterEach() throws Exception {
                subjectDeleteProduct(product.getId());
            }

            @Test
            @DisplayName("Product 목록을 리턴한다.")
            void it_returns_a_product_list() throws Exception {
                subjectGetProduct()
                    .andExpect(status().isOk())
                    .andExpect(content().string(content));
            }
        }
    }
}
