package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Nested
@DisplayName("ProductController 클래스")
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {

        @Nested
        @DisplayName("저장소에 상품들이 있다면")
        class Context_exist_products {

            List<Product> productList = new ArrayList<>();

            @BeforeEach
            void setUp() {

                productList.add(Product.builder()
                        .name("name1")
                        .maker("maker1")
                        .price(1000L)
                        .imgUrl("img2").build());

                productList.add(Product.builder()
                        .name("name2")
                        .maker("maker2")
                        .price(2000L)
                        .imgUrl("img2").build());

                BDDMockito.given(productService.getProducts()).willReturn(productList);

            }

            @Test
            @DisplayName("상품들을 리턴합니다.")
            void It_return_products() throws Exception {

                mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

            }

        }

    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class Describe_createProduct {

        @Nested
        @DisplayName("저장소에 등록할 상품이 있다면")
        class Context_exist_product {

            Product product;
            ProductData productData;

            @BeforeEach
            void setUp() {

                productData = ProductData.builder()
                        .name("name1")
                        .maker("maker1")
                        .price(1000L)
                        .imgUrl("img1")
                        .build();

                product = productService.createProduct(productData);

                BDDMockito.given(productService.createProduct(any(ProductData.class))).willReturn(product);

            }

            @Test
            @DisplayName("저장소에 저장 후 상품을 리턴합니다.")
            void It_save_return_product() throws Exception{

                mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productData)))
                        .andExpect(status().isCreated());

            }

        }

    }

}

