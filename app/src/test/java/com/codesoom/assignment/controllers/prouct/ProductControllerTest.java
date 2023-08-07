package com.codesoom.assignment.controllers.prouct;
import com.codesoom.assignment.domain.product.Product;
import com.codesoom.assignment.domain.product.ProductRepository;
import com.codesoom.assignment.dto.product.ProductData;

import com.codesoom.assignment.infra.product.exception.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("상품을 등록 후 생성된 상품정보를 반환한다.")
    void createProduct() throws Exception {
        // given
        ProductData productData = ProductData.builder().name("catToy").maker("CatMaker").price(1200).imageUrl("test/img.jpg").build();
        String jsonString = objectMapper.writeValueAsString(productData);

        // expected
        mockMvc.perform(post("/products")
                        .contentType(APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("catToy"))
                .andExpect(jsonPath("maker").value("CatMaker"))
                .andExpect(jsonPath("price").value(1200))
                .andExpect(jsonPath("imageUrl").value("test/img.jpg"))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 리스트 요청 시 상품리스트 반환한다.")
    void getProducts() throws Exception {
        // given
        Product product = Product.builder()
                .name("catToy1")
                .price(2000)
                .maker("maker1")
                .imageUrl("test/img1.jpg")
                .build();

        productRepository.save(product);

        // expected
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value("catToy1"))
                .andExpect(jsonPath("[0].maker").value("maker1"))
                .andExpect(jsonPath("[0].price").value(2000))
                .andExpect(jsonPath("[0].imageUrl").value("test/img1.jpg"))
                .andDo(print());
    }

    @Test
    @DisplayName("단일 상품 조회 시 해당 상품정보를 반환한다.")
    void getProduct() throws Exception {
        // given
        Product product = Product.builder()
                .name("catToy1")
                .price(2000)
                .maker("maker1")
                .imageUrl("test/img1.jpg")
                .build();

        Product savedProduct = productRepository.save(product);

        // expected
        mockMvc.perform(get("/products/" + savedProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("catToy1"))
                .andExpect(jsonPath("maker").value("maker1"))
                .andExpect(jsonPath("price").value(2000))
                .andExpect(jsonPath("imageUrl").value("test/img1.jpg"))
                .andDo(print());
    }

    @Test
    @DisplayName("단일 상품 조회 시 없는 경우 ProductNotFound 예외 발생")
    void getProductNotFound() throws Exception {

        // expected
        mockMvc.perform(get("/products/" + 1L))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException))
                .andExpect(jsonPath("message").value(ProductNotFoundException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 수정 요청시 해당 상품정보를 수정한다.")
    void updateProduct() throws Exception {
        // given
        Product product = Product.builder()
                .name("catToy1")
                .price(2000)
                .maker("maker1")
                .imageUrl("test/img1.jpg")
                .build();
        Product savedProduct = productRepository.save(product);
        ProductData productRequest = ProductData.builder().name("update").maker("update").price(3000).imageUrl("test/update.jpg").build();

        // expected
        mockMvc.perform(patch("/products/" + savedProduct.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("update"))
                .andExpect(jsonPath("maker").value("update"))
                .andExpect(jsonPath("price").value(3000))
                .andExpect(jsonPath("imageUrl").value("test/update.jpg"))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 수정 요청시 없는 경우 ProductNotFound 예외 발생")
    void updateProductNotFound() throws Exception {
        // given
        ProductData productData = ProductData.builder().name("update").maker("update").price(3000).imageUrl("test/update.jpg").build();

        // expected
        mockMvc.perform(patch("/products/" + 100L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productData))
                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException))
                .andExpect(jsonPath("message").value(ProductNotFoundException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 삭제 요청 시 해당 상품을 삭제한다.")
    void deleteProducts() throws Exception {
        // given
        Product product = Product.builder()
                .name("deleteTarget")
                .price(2000)
                .maker("deleteMaker")
                .imageUrl("test/delete.jpg")
                .build();
        productRepository.save(product);

        // expected
        mockMvc.perform(delete("/products/" + product.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());

    }

    @Test
    @DisplayName("상품 삭제 요청 시 없는 경우 ProductNotFound 예외 발생")
    void deleteProductsNotFound() throws Exception {
        // expected
        mockMvc.perform(delete("/products/" + 100L))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException))
                .andExpect(jsonPath("message").value(ProductNotFoundException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 생성시 모든 항목의 값이 없는 요청인 경우 에러를 반환한다.")
    void createProductInvalidRequest() throws Exception {
        // given
        ProductData productData = ProductData.builder().name("").maker("").price(0).imageUrl("").build();

        // expected
        mockMvc.perform(post("/products")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productData)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].source").value("name"))
                .andExpect(jsonPath("errors[0].type").value("BAD_REQUEST"))
                .andExpect(jsonPath("errors[0].message").value("상품 이름을 입력해주세요."))
                .andExpect(jsonPath("errors[1].source").value("maker"))
                .andExpect(jsonPath("errors[1].type").value("BAD_REQUEST"))
                .andExpect(jsonPath("errors[1].message").value("상품 제조사를 입력해주세요."))
                .andDo(print());
    }

    @ParameterizedTest
    @DisplayName("상품 생성시 올바르지 않은 요청 케이스별 테스트 요청인 경우 에러응답을 반환한다.")
    @MethodSource("provideInvalidProductRequests")
    void createProductInvalidRequestCase(ProductData productRequest) throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    private static Stream<Arguments> provideInvalidProductRequests() {
        return Stream.of(
                Arguments.of(ProductData.builder().name("").maker("testMaker").price(1000).imageUrl("").build()),
                Arguments.of(ProductData.builder().name("testName").maker("").price(1000).imageUrl("").build()),
                Arguments.of(ProductData.builder().name("testName").maker("testMaker").price(-10).imageUrl("").build())
        );
    }
}