package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.ProductDto;
import com.codesoom.assignment.domain.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ProductSaveController 클래스")
public class ProductSaveControllerMockMvcTest extends ControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final ProductDto PRODUCT_DTO = new ProductDto("dog nose work toy",
            "miki international",
            BigDecimal.valueOf(2000),
            "url");

    @AfterEach
    void cleanup() {
        repository.deleteAll();
    }

    @DisplayName("상품을 성공적으로 추가한다.")
    @Test
    void createTest() throws Exception {
        mockMvc.perform(post("/products")
                .content(objectMapper.writeValueAsString(PRODUCT_DTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(PRODUCT_DTO.getName())));
    }

}
