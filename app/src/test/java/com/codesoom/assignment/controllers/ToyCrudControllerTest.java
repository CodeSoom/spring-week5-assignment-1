package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ToyCrudService;
import com.codesoom.assignment.controllers.dtos.ToyRequestData;
import com.codesoom.assignment.domain.ImageDemo;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyProducer;
import com.codesoom.assignment.domain.Won;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;


import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ToyCrudController")
class ToyCrudControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ToyCrudService service;

    private Toy toy;
    private Toy toyWithoutId;
    private Toy toyUpdating;
    private ToyProducer producer;
    private Won money;
    private ImageDemo demo;
    private final Long TOY_ID = 1L;
    private final Long TOY_ID_NOT_EXISTING = 10L;
    private final Long TOY_PRODUCER_ID = 1L;
    private final String PRODUCT_NAME = "Test Product";
    private final String PRODUCER_NAME = "Test Producer";
    private final BigDecimal MONEY_VALUE = new BigDecimal(1000);
    private final String IMAGE_URL = "https://metacode.biz/@test/avatar.jpg";

    @BeforeEach
    void setUp() {
        demo = new ImageDemo(IMAGE_URL);
        money = new Won(MONEY_VALUE);

        producer = ToyProducer.builder()
                .id(TOY_PRODUCER_ID)
                .name(PRODUCER_NAME)
                .build();
        toy = Toy.builder()
                .id(TOY_ID)
                .name(PRODUCT_NAME)
                .price(money)
                .producer(producer)
                .demo(demo)
                .build();
        toyWithoutId = Toy.builder()
                .name(PRODUCT_NAME)
                .price(money)
                .producer(producer)
                .demo(demo)
                .build();
        toyUpdating = Toy.builder()
                .name(PRODUCT_NAME + "UPDATED")
                .price(money)
                .producer(producer)
                .demo(demo)
                .build();
    }

    @Nested
    @DisplayName("list 메소드는")
    class Describe_list {
        @BeforeEach
        void setUp() {
            given(service.showAll()).willReturn(List.of(toy));
        }

        @Test
        @DisplayName("HTTP Status Code 200 OK 응답한다")
        void it_responds_with_200_ok() throws Exception {
            mockMvc.perform(get("/products"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString(PRODUCT_NAME)));
        }
    }

    @Nested
    @DisplayName("detail 메소드는")
    class Describe_detail {
        @Nested
        @DisplayName("만약 존재하는 Toy를 상세조회한다면")
        class Context_with_existing_toy {
            @BeforeEach
            void setUp() {
                given(service.showById(TOY_ID)).willReturn(toy);
            }

            @Test
            @DisplayName("HTTP Status Code 200 OK 응답한다")
            void it_responds_with_200_ok() throws Exception {
                mockMvc.perform(get("/products/" + TOY_ID))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 Toy를 상세조회한다면")
        class Context_without_existing_toy {
            @BeforeEach
            void setUp() {
                given(service.showById(TOY_ID_NOT_EXISTING))
                        .willThrow(new ProductNotFoundException(TOY_ID_NOT_EXISTING));
            }

            @Test
            @DisplayName("HTTP Status Code 404 NOT FOUND 응답한다")
            void it_responds_with_404() throws Exception {
                mockMvc.perform(get("/products/" + TOY_ID_NOT_EXISTING))
                        .andExpect(status().isNotFound());
            }

        }

        @Nested
        @DisplayName("create 메소드는")
        class Describe_create {
            @BeforeEach
            void setUp() {
                given(service.create(toyWithoutId)).willReturn(toy);
            }

            @Test
            @DisplayName("HTTP Status Code 201 CREATED 응답한다")
            void it_responds_with_201() throws Exception {
                mockMvc.perform(post("/products")
                                .content(toyToJson(toy))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());

            }
        }

        @Nested
        @DisplayName("patch 메소드는")
        class Describe_patch {
            @Nested
            @DisplayName("만약 존재하는 Toy를 부분 수정한다면")
            class Context_with_existing_toy {
                @BeforeEach
                void setUp() {
                    Toy toyUpdated = Toy.builder()
                            .id(TOY_ID)
                            .name(PRODUCT_NAME + "UPDATED")
                            .price(money)
                            .producer(producer)
                            .demo(demo)
                            .build();

                    given(service.update(eq(TOY_ID), any(Toy.class))).willReturn(toyUpdated);
                }

                @Test
                @DisplayName("HTTP Status Code 200 OK 응답한다")
                void it_responds_with_200_ok() throws Exception {
                    mockMvc.perform(patch("/products/" + TOY_ID)
                                    .content(toyToJson(toyUpdating))
                                    .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());
                }
            }

            @Nested
            @DisplayName("만약 존재하지 않는 Toy를 부분 수정한다면")
            class Context_without_existing_toy {
                @BeforeEach
                void setUp() {
                    given(service.update(eq(TOY_ID_NOT_EXISTING), any(Toy.class)))
                            .willThrow(new ProductNotFoundException(TOY_ID_NOT_EXISTING));
                }

                @Test
                @DisplayName("HTTP Status Code 404 NOT FOUND 응답한다")
                void it_responds_with_404() throws Exception {
                    mockMvc.perform(patch("/products/" + TOY_ID_NOT_EXISTING)
                                    .content(toyToJson(toyUpdating))
                                    .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isNotFound());
                }
            }
        }

        @Nested
        @DisplayName("delete 메소드는")
        class Describe_delete {
            @Nested
            @DisplayName("만약 존재하는 Toy를 삭제한다면")
            class Context_with_existing_toy {
                @Test
                @DisplayName("HTTP Status Code 204 NO CONTENT 응답한다")
                void it_responds_with_204() throws Exception {
                    mockMvc.perform(delete("/products/" + TOY_ID))
                            .andExpect(status().isNoContent());
                }
            }

            @Nested
            @DisplayName("만약 존재하지 않는 Toy를 삭제한다면")
            class Context_without_existing_toy {
                @BeforeEach
                void setUp() {
                    willThrow(new ProductNotFoundException(TOY_ID_NOT_EXISTING))
                            .given(service).deleteBy(TOY_ID_NOT_EXISTING);
                }

                @Test
                @DisplayName("HTTP Status Code 404 NOT FOUND 응답한다")
                void it_responds_with_404() throws Exception {
                    mockMvc.perform(delete("/products/" + TOY_ID_NOT_EXISTING))
                            .andExpect(status().isNotFound());
                }

            }
        }
    }

    private String toyToJson(Toy toy) throws JsonProcessingException {
        ToyRequestData requestData = ToyRequestData.builder()
                .name(toy.getName())
                .price(toy.getPrice().getValue())
                .maker(toy.getProducer().getName())
                .url(toy.getDemo().getUrl())
                .build();

        return objectMapper.writeValueAsString(requestData);
    }
}
