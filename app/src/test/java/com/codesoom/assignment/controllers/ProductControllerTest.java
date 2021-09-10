package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.utils.Parser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.codesoom.assignment.constants.ProductConstants.PRODUCT_LIST;
import static com.codesoom.assignment.constants.ProductConstants.EMPTY_LIST;

@WebMvcTest(ProductController.class)
@DisplayName("ProductController 클래스")
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @AfterEach
    private void afterEach() {
        reset(productService);
    }

    @Nested
    @DisplayName("list 메서드는")
    public class Describe_list {
        private ResultActions subject() throws Exception {
            return mockMvc.perform(
                get("/products")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            );
        }

        private OngoingStubbing<List<Product>> mockSubject() {
            return when(productService.getProducts());
        }

        @AfterEach
        private void afterEach() {
            verify(productService).getProducts();
        }

        @Nested
        @DisplayName("저장된 Product가 없다면")
        public class Context_product_empty {
            @BeforeEach
            private void beforeEach() {
                mockSubject()
                    .thenReturn(EMPTY_LIST);
            }

            @Test
            @DisplayName("빈 목록을 리턴한다.")
            public void it_returns_a_empty_list() throws Exception {
                subject()
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                        Parser.toJson(EMPTY_LIST)
                    ));
            }
        }

        @Nested
        @DisplayName("저장된 Product가 있다면")
        public class Context_product_exist {
            @BeforeEach
            private void beforeEach() {
                mockSubject()
                    .thenReturn(PRODUCT_LIST);
            }

            @Test
            @DisplayName("Product 목록을 리턴한다.")
            public void it_returns_a_product_list() throws Exception {
                subject()
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                        Parser.toJson(PRODUCT_LIST)
                    ));
            }
        }
    }
}
