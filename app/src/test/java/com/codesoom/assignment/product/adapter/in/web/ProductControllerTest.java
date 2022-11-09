package com.codesoom.assignment.product.adapter.in.web;

import com.codesoom.assignment.product.adapter.in.web.dto.ProductResponse;
import com.codesoom.assignment.utils.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.codesoom.assignment.support.IdFixture.ID_MAX;
import static com.codesoom.assignment.support.ProductFixture.TOY_1;
import static com.codesoom.assignment.support.ProductFixture.TOY_2;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 통합 웹 테스트")
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductController productController;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class list_메서드는 {
        @Test
        @DisplayName("200 코드를 반환한다")
        void it_responses_200() throws Exception {
            mockMvc.perform(
                            get("/products")
                    )
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class detail_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            @Test
            @DisplayName("200 코드를 반환한다")
            void it_responses_200() throws Exception {
                ProductResponse productSource = productController.create(TOY_1.요청_데이터_생성());

                mockMvc.perform(
                                get("/products/" + productSource.getId())
                        )
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(TOY_1.NAME())))
                        .andExpect(content().string(containsString(TOY_1.MAKER())));
                ;
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("404 코드를 반환한다")
            void it_responses_404() throws Exception {
                mockMvc.perform(
                                get("/products/" + ID_MAX.value())
                        )
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class create_메서드는 {
        @Test
        @DisplayName("201 코드를 반환한다")
        void it_responses_201() throws Exception {
            mockMvc.perform(
                            post("/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(JsonUtil.writeValue(TOY_1.요청_데이터_생성()))
                    )
                    .andExpect(status().isCreated())
                    .andExpect(content().string(containsString(TOY_1.NAME())))
                    .andExpect(content().string(containsString(TOY_1.MAKER())));
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class update_메서드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            private Long fixtureId;

            @BeforeEach
            void setUpCreateFixture() {
                ProductResponse productSource = productController.create(TOY_1.요청_데이터_생성());
                fixtureId = productSource.getId();
            }

            @Test
            @DisplayName("200 코드를 반환한다")
            void it_responses_200() throws Exception {
                mockMvc.perform(
                                put("/products/" + fixtureId)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(JsonUtil.writeValue(TOY_2.요청_데이터_생성()))
                        )
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(TOY_2.NAME())))
                        .andExpect(content().string(containsString(TOY_2.MAKER())));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("404 코드를 반환한다")
            void it_responses_404() throws Exception {
                mockMvc.perform(
                                put("/products/" + ID_MAX.value())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(JsonUtil.writeValue(TOY_2.요청_데이터_생성()))
                        )
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class delete_메서드는 {
        private Long fixtureId;

        @BeforeEach
        void setUpCreateFixture() {
            ProductResponse productSource = productController.create(TOY_1.요청_데이터_생성());
            fixtureId = productSource.getId();
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            @Test
            @DisplayName("204 코드를 반환한다")
            void it_responses_204() throws Exception {
                mockMvc.perform(
                                delete("/products/" + fixtureId)
                        )
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("404 코드를 반환한다")
            void it_responses_404() throws Exception {
                mockMvc.perform(
                                delete("/products/" + ID_MAX.value())
                        )
                        .andExpect(status().isNotFound());
            }
        }
    }
}
