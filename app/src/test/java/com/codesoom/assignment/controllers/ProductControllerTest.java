package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@WebMvcTest(ProductController.class)
@DisplayName("ProductController 테스트")
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final String SETUP_PRODUCT_NAME = "setupName";
    private final String SETUP_PRODUCT_MAKER = "setupMaker";
    private final Integer SETUP_PRODUCT_PRICE = 100;
    private final String SETUP_PRODUCT_IMAGEURL = "setupImage";

    private final String CREATED_PRODUCT_NAME = "createdName";
    private final String CREATED_PRODUCT_MAKER = "createdMaker";
    private final Integer CREATED_PRODUCT_PRICE = 200;
    private final String CREATED_PRODUCT_IMAGEURL = "createdImage";

    private final String UPDATED_PRODUCT_NAME = "updatedName";
    private final String UPDATED_PRODUCT_MAKER = "updatedMaker";
    private final Integer UPDATED_PRODUCT_PRICE = 300;
    private final String UPDATED_PRODUCT_IMAGEURL = "updatedImage";

    private final Long EXISTED_ID = 1L;
    private final Long CREATED_ID = 2L;
    private final Long NOT_EXISTED_ID = 100L;

    private List<Product> products;
    private Product setupProduct;
    private Product createdProduct;

    @BeforeEach
    void setUp() {
        setupProduct = Product.builder()
                .id(EXISTED_ID)
                .name(SETUP_PRODUCT_NAME)
                .maker(SETUP_PRODUCT_MAKER)
                .price(SETUP_PRODUCT_PRICE)
                .imageUrl(SETUP_PRODUCT_IMAGEURL)
                .build();

        createdProduct = Product.builder()
                .id(CREATED_ID)
                .name(CREATED_PRODUCT_NAME)
                .maker(CREATED_PRODUCT_MAKER)
                .price(CREATED_PRODUCT_PRICE)
                .imageUrl(CREATED_PRODUCT_IMAGEURL)
                .build();

        products = Arrays.asList(setupProduct, createdProduct);
    }

    @Nested
    @DisplayName("list 메서드는")
    class Describe_list {

        @Test
        @DisplayName("전체 고양이 장난감 목록과 OK를 리턴한다")
        void itReturnsProductsAndOKHttpStatus() throws Exception {
            given(productService.getProducts()).willReturn(products);

            mockMvc.perform(get("/products"))
                    .andExpect(content().string(containsString(SETUP_PRODUCT_NAME)))
                    .andExpect(content().string(containsString(CREATED_PRODUCT_NAME)))
                    .andExpect(status().isOk());

            verify(productService).getProducts();
        }
    }

    @Nested
    @DisplayName("detail 메서드는")
    class Describe_detail {
        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감의 아이디가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감과 OK를 리턴한다")
            void itReturnsProductAndOKHttpStatus() throws Exception {
                given(productService.getProduct(givenExistedId)).willReturn(setupProduct);

                mockMvc.perform(get("/products/"+ givenExistedId))
                        .andDo(print())
                        .andExpect(jsonPath("id").value(givenExistedId))
                        .andExpect(status().isOk());

                verify(productService).getProduct(givenExistedId);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 고양이 장난감의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("고양이 장난감을 찾을 수 없다는 메세지와 NOT_FOUND를 응답한다")
            void itReturnsNotFoundMessageAndNOT_FOUNDHttpStatus() throws Exception {
                given(productService.getProduct(givenNotExistedId))
                        .willThrow(ProductNotFoundException.class);

                mockMvc.perform(get("/products/"+ givenNotExistedId))
                        .andDo(print())
                        .andExpect(status().isNotFound())
                        .andExpect(content().string(containsString("Product not found")));

                verify(productService).getProduct(givenNotExistedId);
            }
        }
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {
        @Nested
        @DisplayName("만약 고양이 장난감 객체가 주어진다면")
        class Context_WithProduct {
            private Product createdProduct;

            @BeforeEach
            void setUp() {
                createdProduct = Product.builder()
                        .name(CREATED_PRODUCT_NAME)
                        .maker(CREATED_PRODUCT_MAKER)
                        .price(CREATED_PRODUCT_PRICE)
                        .imageUrl(CREATED_PRODUCT_IMAGEURL)
                        .build();
            }

            @Test
            @DisplayName("객체를 저장하고 저장된 객체와 CREATED를 리턴한다")
            void itSaveProductAndReturnsSavedProductAndCreatedHttpStatus() throws Exception {
                given(productService.createProduct(any(ProductData.class))).willReturn(createdProduct);

                mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"createdName\" , \"maker\":\"createdMaker\", \"price\":200, \"image\":\"createdImage\"}"))
                        .andDo(print())
                        .andExpect(jsonPath("name").value(createdProduct.getName()))
                        .andExpect(jsonPath("maker").value(createdProduct.getMaker()))
                        .andExpect(jsonPath("price").value(createdProduct.getPrice()))
                        .andExpect(jsonPath("imageUrl").value(createdProduct.getImageUrl()))
                        .andExpect(status().isCreated());

                verify(productService).createProduct(any(ProductData.class));
            }
        }

        @Nested
        @DisplayName("만약 이름값이 비어있는 고양이 장난감 객체가 주어진다면")
        class Context_WithProductWithoutName {
            @Test
            @DisplayName("요청이 잘못됐다는 메세지와 BAD_REQUEST를 리턴한다")
            void itReturnsBadRequestMessageAndBAD_REQUESTHttpStatus() throws Exception {
                mockMvc.perform(post("/products").accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\" , \"maker\":\"createdMaker\", \"price\":200, \"image\":\"createdImage\"}"))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("name 값은 필수입니다")));
            }
        }

        @Nested
        @DisplayName("만약 메이커값이 비어있는 고양이 장난감 객체가 주어진다면")
        class Context_WithProductWithoutMaker {
            @Test
            @DisplayName("요청이 잘못됐다는 메세지와 BAD_REQUEST를 리턴한다")
            void itReturnsBadRequestMessageAndBAD_REQUESTHttpStatus() throws Exception {
                mockMvc.perform(post("/products").accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"createdName\" , \"maker\":\"\", \"price\":200, \"image\":\"createdImage\"}"))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("maker 값은 필수입니다")));
            }
        }

        @Nested
        @DisplayName("만약 가격값이 비어있는 고양이 장난감 객체가 주어진다면")
        class Context_WithProductWithoutPrice {
            @Test
            @DisplayName("요청이 잘못됐다는 메세지와 BAD_REQUEST를 리턴한다")
            void itReturnsBadRequestMessageAndBAD_REQUESTHttpStatus() throws Exception {
                mockMvc.perform(post("/products").accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"createdName\" , \"maker\":\"createdMaker\", \"price\": null, \"image\":\"createdImage\"}"))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("price 값은 필수입니다")));
            }
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {
        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감의 아이디와 업데이트 될 객체가 주어진다면")
        class Context_WithExistedIdAndNameAndMakerAndPriceAndImage {
            private final Long givenExistedId = EXISTED_ID;
            private Product updatedProduct;

            @BeforeEach
            void setUp() {
                updatedProduct = Product.builder()
                        .name(UPDATED_PRODUCT_NAME)
                        .maker(UPDATED_PRODUCT_MAKER)
                        .price(UPDATED_PRODUCT_PRICE)
                        .imageUrl(UPDATED_PRODUCT_IMAGEURL)
                        .build();
            }

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감을 업데이트하고 해당 객체와 OK를 리턴한다")
            void itUpdatesProductAndReturnsUpdatedProductAndOKHttpStatus() throws Exception {
                given(productService.updateProduct(eq(givenExistedId), any(ProductData.class))).willReturn(updatedProduct);

                mockMvc.perform(patch("/products/" + givenExistedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"updatedName\" , \"maker\":\"updatedMaker\", \"price\":300, \"image\":\"updatedImage\"}"))
                        .andDo(print())
                        .andExpect(jsonPath("name").value(updatedProduct.getName()))
                        .andExpect(jsonPath("maker").value(updatedProduct.getMaker()))
                        .andExpect(jsonPath("price").value(updatedProduct.getPrice()))
                        .andExpect(jsonPath("imageUrl").value(updatedProduct.getImageUrl()))
                        .andExpect(status().isOk());

                verify(productService).updateProduct(eq(givenExistedId), any(ProductData.class));
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않는 고양이 장난감의 아이디가 주어진다면")
        class Context_WithOutExistedId {
            private final Long givenNotExisted = NOT_EXISTED_ID;

            @Test
            @DisplayName("고양이 장난감을 찾을 수 없다는 메세지와 NOT_FOUND를 응답한다")
            void itThrowsProductNotFoundExceptionAndNOT_FOUNDHttpStatus() throws Exception {
                given(productService.updateProduct(eq(givenNotExisted), any(ProductData.class)))
                        .willThrow(ProductNotFoundException.class);

                mockMvc.perform(patch("/products/" + givenNotExisted)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"updatedName\" , \"maker\":\"updatedMaker\", \"price\":300, \"image\":\"updatedImage\"}"))
                        .andDo(print())
                        .andExpect(status().isNotFound());

                verify(productService).updateProduct(eq(givenNotExisted), any(ProductData.class));
            }
        }

        @Nested
        @DisplayName("만약 이름값이 비어있는 고양이 장난감 객체가 주어진다면")
        class Context_WithOutName {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("요청이 잘못 됐다는 메세지와 BAD_REQUEST를 응답한다")
            void itThrowsProductNotFoundExceptionAndBAD_REQUESTHttpStatus() throws Exception {
                mockMvc.perform(patch("/products/" + givenExistedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\" , \"maker\":\"updatedMaker\", \"price\":300, \"image\":\"updatedImage\"}"))
                        .andDo(print())
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("만약 메이커값이 비어있는 고양이 장난감 객체가 주어진다면")
        class Context_WithOutMaker {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("요청이 잘못 됐다는 메세지와 BAD_REQUEST를 응답한다")
            void itThrowsProductNotFoundExceptionAndBAD_REQUESTHttpStatus() throws Exception {
                mockMvc.perform(patch("/products/" + givenExistedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"updatedName\" , \"maker\":\"\", \"price\":300, \"image\":\"updatedImage\"}"))
                        .andDo(print())
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("만약 가격값이 비어있는 고양이 장난감 객체가 주어진다면")
        class Context_WithOutPrice {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("요청이 잘못 됐다는 메세지와 BAD_REQUEST를 응답한다")
            void itThrowsProductNotFoundExceptionAndBAD_REQUESTHttpStatus() throws Exception {
                mockMvc.perform(patch("/products/" + givenExistedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"updatedName\" , \"maker\":\"\", \"price\": null, \"image\":\"updatedImage\"}"))
                        .andDo(print())
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {
        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감의 아이디가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감을 삭제하고 삭제된 고양이 장난감과 NO_CONTENT를 리턴한다")
            void itDeleteProductAndReturnsNO_CONTENTHttpStatus() throws Exception {
                given(productService.deleteProduct(givenExistedId)).willReturn(setupProduct);

                mockMvc.perform(delete("/products/" + givenExistedId))
                        .andDo(print())
                        .andExpect(jsonPath("id").value(givenExistedId))
                        .andExpect(status().isNoContent());

                verify(productService).deleteProduct(givenExistedId);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 고양이 장난감의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("고양이 장난감을 찾을 수 없다는 메세지와 NOT_FOUND를 리턴한다")
            void itReturnsNotFoundMessageAndNOT_FOUNDHttpStatus() throws Exception {
                given(productService.deleteProduct(givenNotExistedId))
                        .willThrow(ProductNotFoundException.class);

                mockMvc.perform(delete("/products/" + givenNotExistedId))
                        .andDo(print())
                        .andExpect(status().isNotFound())
                        .andExpect(content().string(containsString("Product not found")));

                verify(productService).deleteProduct(givenNotExistedId);
            }
        }
    }
}
