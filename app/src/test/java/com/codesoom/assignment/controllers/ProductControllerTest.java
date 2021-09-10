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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.codesoom.assignment.constants.ProductConstants.PRODUCT_LIST;

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

        private OngoingStubbing<List<Product>> mockGetProducts() {
            return when(productService.getProducts());
        }

        private ProductService verifyGetProducts(final int invokeCounts) {
            return verify(productService, times(invokeCounts));
        }

        @BeforeEach
        private void beforeEach() {
            mockGetProducts()
                .thenReturn(PRODUCT_LIST);
        }

        @AfterEach
        private void afterEach() {
            verifyGetProducts(1)
                .getProducts();
        }

        @Test
        @DisplayName("ProductService getProducts 메서드의 리턴값을 리턴한다.")
        public void it_returns_a_product_list() throws Exception {
            subject()
                .andExpect(status().isOk())
                .andExpect(content().string(
                    Parser.toJson(PRODUCT_LIST)
                ));
        }
    }
}
