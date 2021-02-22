package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();

        Product product = Product.builder()
                .name("뱀 장난감")
                .maker("야옹이네 장난감")
                .price(3000)
                .image("https://bit.ly/3qzXRME")
                .build();

        Product updated = Product.builder()
                .name("물고기 장난감")
                .maker("애옹이네 장난감")
                .price(5000)
                .image("https://bit.ly/2M4YXkw")
                .build();

        products.add(product);

        given(productService.getProducts()).willReturn(products);

        given(productService.getProduct(1L)).willReturn(product);

        given(productService.getProduct(100L))
                .willThrow(new ProductNotFoundException(100L));

        given(productService.createProduct(any(Product.class)))
                .willReturn(product);

        given(productService.updateProduct(eq(1L), any(Product.class)))
                .willReturn(updated);

        given(productService.updateProduct(eq(100L), any(Product.class)))
                .willThrow(new ProductNotFoundException(100L));

        given(productService.deleteProduct(100L))
                .willThrow(new ProductNotFoundException(100L));
    }

    @Test
    void list() throws Exception {
        mockMvc.perform(get("/products")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("야옹이네 장난감")));

        verify(productService).getProducts();
    }

    @Test
    void detailWithValidId() throws Exception {
        mockMvc.perform(get("/products/1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("뱀 장난감")));

        verify(productService).getProduct(1L);
    }

    @Test
    void detailWithInvalidId() throws Exception {
        mockMvc.perform(get("/products/100"))
                .andExpect(status().isNotFound());

        verify(productService).getProduct(100L);
    }

    @Test
    void createWithValidAttributes() throws Exception {
        mockMvc.perform(
                post("/products")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"뱀 장난감\", \"maker\": \"야옹이네 장난감\", " +
                                "\"price\": 3000, \"image\": \"https://bit.ly/3qzXRME\"}")
        )
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("뱀 장난감")));

        verify(productService).createProduct(any(Product.class));
    }

    @Test
    void createWithInvalidAttributes() throws Exception {
        mockMvc.perform(
                post("/products")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\", \"maker\": \"\", " +
                                "\"price\": 0, \"image\": \"\"}")
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWithExistProduct() throws Exception {
        mockMvc.perform(
                patch("/products/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"물고기 장난감\", \"maker\": \"애옹이네 장난감\", " +
                                "\"price\": 5000, \"image\": \"https://bit.ly/2M4YXkw\"}")
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("애옹이네 장난감")));

        verify(productService).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void updateWithNotExistedProduct() throws Exception {
        mockMvc.perform(
                patch("/products/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"물고기 장난감\", \"maker\": \"애옹이네 장난감\", " +
                                "\"price\": 5000, \"image\": \"https://bit.ly/2M4YXkw\"}")
        )
                .andExpect(status().isNotFound());

        verify(productService).updateProduct(eq(100L), any(Product.class));
    }

    @Test
    void updateWithInvalidAttributes() throws Exception {
        mockMvc.perform(
                patch("/products/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\", \"maker\": \"\", " +
                                "\"price\": 0, \"image\": \"\"}")
        )
                .andExpect(status().isBadRequest());
    }


    @Test
    void deleteWithExistedProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());

        verify(productService).deleteProduct(1L);
    }

    @Test
    void deleteWithNotExistedProduct() throws Exception {
        mockMvc.perform(delete("/products/100"))
                .andExpect(status().isNotFound());

        verify(productService).deleteProduct(100L);
    }
}
