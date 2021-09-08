package com.codesoom.assignment.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.codesoom.assignment.domain.ProductConstants.PRODUCT_DATA;
import static com.codesoom.assignment.domain.ProductConstants.EMPTY_LIST;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.utils.Parser;
import com.google.common.collect.Lists;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("Product 리소스")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class ProductWebTest {
    @Autowired
    private MockMvc mockMvc;

    private String requestBody;
    private Long requestParameter;

    private ResultActions subjectGetProduct() throws Exception {
        return mockMvc.perform(
            get("/products")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );
    }

    private ResultActions subjectPostProduct() throws Exception {
        return mockMvc.perform(
            post("/products")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );
    }

    private ResultActions subjectDeleteProduct() throws Exception {
        return mockMvc.perform(
            delete("/products/" + requestParameter)
        );
    }

    @Nested
    @DisplayName("전체 목록 조회 엔드포인트는")
    class Describe_get_products {

        @Nested
        @DisplayName("저장된 Product가 없다면")
        class Context_product_empty {
            @Test
            @DisplayName("빈 목록을 리턴한다.")
            void it_returns_a_empty_list() throws Exception {
                subjectGetProduct()
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                        Parser.toJson(EMPTY_LIST)
                    ));
            }
        }

        @Nested
        @DisplayName("저장된 Product가 있다면")
        class Context_product_exist {
            private Product product;

            @BeforeEach
            void beforeEach() throws Exception {
                requestBody = Parser.toJson(PRODUCT_DATA);
                product = Parser.toObject(
                    subjectPostProduct()
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), Product.class
                );
                requestParameter = product.getId();
            }

            @AfterEach
            void afterEach() throws Exception {
                subjectDeleteProduct();
            }

            @Test
            @DisplayName("Product 목록을 리턴한다.")
            void it_returns_a_product_list() throws Exception {
                subjectGetProduct()
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                        Parser.toJson(Lists.newArrayList(product))
                    ));
            }
        }
    }
}
