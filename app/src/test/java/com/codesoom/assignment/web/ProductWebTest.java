package com.codesoom.assignment.web;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.codesoom.assignment.constants.ProductConstants.PRODUCT_LIST;
import static com.codesoom.assignment.constants.ProductConstants.PRODUCT_ENDPOINT;

import java.util.List;

import com.codesoom.assignment.controllers.ProductController;
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

@DisplayName("Product 리소스")
@WebMvcTest(ProductController.class)
public class ProductWebTest {
    @Autowired
    private MockMvc mockMvc;

    private String requestBody;
    private Long requestParameter;

    @MockBean
    private ProductController productController;

    private ResultActions subjectGetProduct() throws Exception {
        return mockMvc.perform(
            get(PRODUCT_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );
    }

    private ResultActions subjectPostProduct() throws Exception {
        return mockMvc.perform(
            post(PRODUCT_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );
    }

    private ResultActions subjectDeleteProduct() throws Exception {
        return mockMvc.perform(
            delete(PRODUCT_ENDPOINT + requestParameter)
        );
    }

    @Nested
    @DisplayName("전체 목록 조회 엔드포인트는")
    public class Describe_get_products {
        private OngoingStubbing<List<Product>> mockList() {
            return when(productController.list());
        }

        private ProductController verifyList(final int invokeCounts) {
            return verify(productController, times(invokeCounts));
        }

        @BeforeEach
        private void beforeEach() {
            mockList()
                .thenReturn(PRODUCT_LIST);
        }

        @AfterEach
        private void afterEach() {
            verifyList(1)
                .list();
        }

        @Test
        @DisplayName("ProductController list 메서드의 리턴값을 JSON 문자열로 변환하여 리턴한다.")
        public void it_returns_a_product_list_json_string() throws Exception {
            subjectGetProduct()
                .andExpect(status().isOk())
                .andExpect(content().string(
                    Parser.toJson(PRODUCT_LIST)
                ));
        }
    }
}
