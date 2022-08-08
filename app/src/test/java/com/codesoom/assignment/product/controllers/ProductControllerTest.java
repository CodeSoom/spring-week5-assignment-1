package com.codesoom.assignment.product.controllers;

import com.codesoom.assignment.product.domain.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스의")
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    private ResultActions create(Map<String, Object> input) throws Exception {
        return mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));
    }

    private Map<String, Object> createToyAndConvert(Map<String, Object> input) throws Exception {
        return objectMapper.readValue(
                create(input).andReturn()
                        .getResponse()
                        .getContentAsString(),
                new TypeReference<>() {
                });
    }

    abstract static class Normal {
        public static final String NAME = "toyName";
        public static final String MAKER = "codesoom";
        public static final int PRICE = 2200000;
        public static final String URL = "www.picture.com";
        public static final String SALE = "SALE";

        Map<String, Object> normalInput() {
            HashMap<String, Object> input = new HashMap<>();

            input.put("name", NAME);
            input.put("maker", MAKER);
            input.put("price", PRICE);
            input.put("imageUrl", URL);
            input.put("status", SALE);

            return input;
        }
    }

    @Nested
    @DisplayName("POST /products 요청은")
    class Describe_create {
        @Nested
        @DisplayName("상품 정보가 주어지면")
        class Context_with_productData extends Normal {
            @Test
            @DisplayName("상품을 리턴한다")
            void It_returns_product() throws Exception {
                create(normalInput())
                        .andExpect(jsonPath("$.name").value(NAME))
                        .andExpect(jsonPath("$.maker").value(MAKER))
                        .andExpect(jsonPath("$.price").value(PRICE))
                        .andExpect(jsonPath("$.imageUrl").value(URL))
                        .andExpect(status().isCreated());
            }
        }

        @Nested
        @DisplayName("상품 정보의 이름이 주어지지 않았다면")
        class Context_without_Name_Maker_Price extends Normal {
            Map<String, Object> prepare(String input) {
                Map<String, Object> inValidInput = normalInput();

                inValidInput.put("name", input);

                return inValidInput;
            }

            void expect(Map<String, Object> inValidInput) throws Exception {
                create(inValidInput)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.[0].fieldName", Is.is("name")))
                        .andExpect(jsonPath("$.[0].message").isString());
            }

            @ParameterizedTest
            @ValueSource(strings = {"", " ", "\t", "\n", "  "})
            @NullSource
            @DisplayName("예외 메시지를 응답한다")
            void It_returns_errorResponse(String input) throws Exception {
                Map<String, Object> inValidInput = prepare(input);

                expect(inValidInput);
            }
        }

        @Nested
        @DisplayName("상품 이름이 길이 제한을 만족하지 않는다면")
        class Context_with_invalidName extends Normal {
            Map<String, Object> prepare(String input) {
                Map<String, Object> inValidInput = normalInput();

                inValidInput.put("name", input);

                return inValidInput;
            }

            private void expect(Map<String, Object> input) throws Exception {
                create(input)
                        .andExpect(jsonPath("$.[0].fieldName", Is.is("name")))
                        .andExpect(jsonPath("$.[0].message", Is.is("이름의 길이가 범위를 벗어납니다.")))
                        .andExpect(status().isBadRequest());
            }

            @ParameterizedTest
            @ValueSource(strings = {"얍", "길이가 범위를 넘는 값을 가진 이름입니다."})
            @DisplayName("예외 메시지를 응답한다")
            void It_returns_exceptionResponse(String input) throws Exception {
                Map<String, Object> inValidInput = prepare(input);

                expect(inValidInput);
            }
        }

        @Nested
        @DisplayName("상품 정보에 메이커 값이 없으면")
        class Context_without_maker extends Normal {
            Map<String, Object> prepare(String input) {
                Map<String, Object> inValidInput = normalInput();

                inValidInput.put("maker", input);

                return inValidInput;
            }

            void expect(Map<String, Object> input) throws Exception {
                create(input)
                        .andExpect(jsonPath("$.[0].fieldName", Is.is("maker")))
                        .andExpect(jsonPath("$.[0].message", Is.is("메이커는 필수값입니다.")))
                        .andExpect(status().isBadRequest());
            }

            @ParameterizedTest
            @ValueSource(strings = {"", " ", "\t", "\n", "  "})
            @NullSource
            @DisplayName("에외 메시지를 응답한다")
            void It_returns_exceptionResponse(String input) throws Exception {
                Map<String, Object> inValidInput = prepare(input);

                expect(inValidInput);
            }
        }

        @Nested
        @DisplayName("상품 메이커명의 길이가 범위를 벗어난다면")
        class Context_outOfRangeMaker extends Normal {
            Map<String, Object> prepare(String input) {
                Map<String, Object> inValidInput = normalInput();

                inValidInput.put("maker", input);

                return inValidInput;
            }

            private void expect(Map<String, Object> input) throws Exception {
                create(input)
                        .andExpect(jsonPath("$.[0].fieldName", Is.is("maker")))
                        .andExpect(jsonPath("$.[0].message", Is.is("메이커 길이가 범위를 벗어납니다.")))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("예외 메시지를 응답한다")
            void It_returns_errorResponse() throws Exception {
                Map<String, Object> inValidInput = prepare("이름의 범위가 벗어남");

                expect(inValidInput);
            }
        }

        @Nested
        @DisplayName("상품 가격이 주어지지 않는다면")
        class Context_without_price extends Normal {
            Map<String, Object> prepare(Integer input) {
                Map<String, Object> inValidInput = normalInput();

                inValidInput.put("price", input);

                return inValidInput;
            }

            private void expect(Map<String, Object> input) throws Exception {
                create(input)
                        .andExpect(jsonPath("$.[0].fieldName", Is.is("price")))
                        .andExpect(jsonPath("$.[0].message", Is.is("가격은 필수값입니다.")))
                        .andExpect(status().isBadRequest());
            }

            @ParameterizedTest
            @NullSource
            @DisplayName("예외 메시지를 응답한다")
            void It_returns_errorResponse(Integer input) throws Exception {
                Map<String, Object> inValidInput = prepare(input);

                expect(inValidInput);
            }
        }

        @Nested
        @DisplayName("상품 가격이 음수로 주어지면")
        class Context_with_negative extends Normal {
            Map<String, Object> prepare() {
                Map<String, Object> inValidInput = normalInput();

                inValidInput.put("price", Integer.MIN_VALUE);

                return inValidInput;
            }

            private void expect(Map<String, Object> input) throws Exception {
                create(input)
                        .andExpect(jsonPath("$.[0].fieldName", Is.is("price")))
                        .andExpect(jsonPath("$.[0].message", Is.is("가격은 음수일 수 없습니다.")))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("예외 메시지를 응답한다")
            void It_returns_errorResponse() throws Exception {
                Map<String, Object> inValidInput = prepare();
                expect(inValidInput);

                inValidInput.put("price", -1);
                expect(inValidInput);
            }
        }

        @Nested
        @DisplayName("상품 가격이 한도를 초과하면")
        class Context_with_priceExceedLimit extends Normal {
            Map<String, Object> prepare() {
                Map<String, Object> inValidInput = normalInput();

                inValidInput.put("price", Integer.MAX_VALUE);

                return inValidInput;
            }

            private void expect(Map<String, Object> input) throws Exception {
                create(input)
                        .andExpect(jsonPath("$.[0].fieldName", Is.is("price")))
                        .andExpect(jsonPath("$.[0].message", Is.is("가격의 한계치를 벗어났습니다.")))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("예외 메시지를 응답한다")
            void It_returns_errorResponse() throws Exception {
                Map<String, Object> inValidInput = prepare();
                expect(inValidInput);

                inValidInput.put("price", 10000001);
                expect(inValidInput);
            }
        }

        @Nested
        @DisplayName("상태가 비어있으면")
        class Context_without_status extends Normal {
            Map<String, Object> prepare(String input) {
                Map<String, Object> normalInput = normalInput();
                normalInput.put("status", input);
                return normalInput;
            }

            @ParameterizedTest
            @ValueSource(strings = {"", " ", "  ", "\t", "\n"})
            @NullSource
            @DisplayName("예외 메시지를 응답한다")
            void It_returns_errorResponse(String input) throws Exception {
                create(prepare(input))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.[0].fieldName", Is.is("status")))
                        .andExpect(jsonPath("$.[0].message").isString());

            }
        }
    }

    @Nested
    @DisplayName("GET /products/{id} 요청은")
    class Describe_getDetail {
        @Nested
        @DisplayName("식별자를 가진 상품이 있다면")
        class Context_with_product extends Normal {
            Map<String, Object> createdToy;

            void prepare() throws Exception {
                createdToy = createToyAndConvert(normalInput());
            }

            @Test
            @DisplayName("상품과 상태코드 200을 응답한다")
            void It_returns_productAndOk() throws Exception {
                prepare();

                mockMvc.perform(get("/products/" + createdToy.get("id")))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(jsonPath("$.id", Is.is(createdToy.get("id"))))
                        .andExpect(jsonPath("$.name").value(createdToy.get("name")))
                        .andExpect(jsonPath("$.maker").value(createdToy.get("maker")))
                        .andExpect(jsonPath("$.price", Is.is(createdToy.get("price"))))
                        .andExpect(jsonPath("$.imageUrl", Is.is(createdToy.get("imageUrl"))));
            }
        }

        @Nested
        @DisplayName("식별자를 가진 상품이 없다면")
        class Context_without_product {
            @Test
            @DisplayName("예외 메시지와 상태코드 404를 응답한다")
            void It_returns_exceptionMessageAndNotFound() throws Exception {
                mockMvc.perform(get("/products/" + -1L))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.message").isString());
            }
        }
    }

    @Nested
    @DisplayName("GET /products 요청은")
    class Describe_getAll {
        @Nested
        @DisplayName("상품 목록이 있으면")
        class Context_with_productList extends Normal {
            Map<String, Object> invalidStatusInput() {
                Map<String, Object> input = normalInput();

                input.put("status", null);

                return input;
            }

            void prepare() throws Exception {
                create(invalidStatusInput());
                create(normalInput());
                create(normalInput());
            }

            @Test
            @DisplayName("판매 중인 상품 목록과 상태코드 200을 응답한다")
            void It_returns_productListAndOk() throws Exception {
                prepare();

                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(jsonPath("$", hasSize(2)));
            }
        }
    }

    @Nested
    @DisplayName("GET /products/sold-out 요청은")
    class Describe_getAllSoldOut {
        @Nested
        @DisplayName("상품 목록이 주어지면")
        class Context_with_productList extends Normal {
            Map<String, Object> makeInput(String status) {
                Map<String, Object> normalInput = normalInput();
                normalInput.put("status", status);
                return normalInput;
            }

            void prepare() throws Exception {
                create(makeInput("SOLD_OUT"));
                create(makeInput("SOLD_OUT"));
                create(makeInput("SALE"));
            }

            @Test
            @DisplayName("품절된 상품 목록과 상태코드 200을 응답한다")
            void It_returns_soldOutProductList() throws Exception {
                prepare();

                mockMvc.perform(get("/products/sold-out"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(2)));
            }
        }
    }

    @Nested
    @DisplayName("PUT /products/{id}")
    class Describe_update {
        @Nested
        @DisplayName("변경할 상품 정보가 주어지면")
        class Context_with_productData extends Normal {
            Map<String, Object> createProduct() throws Exception {
                return createToyAndConvert(normalInput());
            }

            Map<String, Object> dataToChange = Map.of(
                    "name", "변경된 이름",
                    "maker", "변경된 메이커",
                    "price", 9999,
                    "imageUrl", "codesoom.com",
                    "status", "SOLD_OUT"
            );

            @Test
            @DisplayName("변경된 상품과 상태코드 200을 응답한다")
            void It_returns_changedProduct() throws Exception {
                Object findId = createProduct().get("id");

                mockMvc.perform(put("/products/" + findId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dataToChange)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id", Is.is(findId)))
                        .andExpect(jsonPath("$.name", Is.is("변경된 이름")))
                        .andExpect(jsonPath("$.price", Is.is(9999)))
                        .andExpect(jsonPath("$.status", Is.is("SOLD_OUT")));
            }
        }
    }

    @Nested
    @DisplayName("DELETE /products/{id} 요청은")
    class Describe_delete {
        @Nested
        @DisplayName("상품이 있다면")
        class Context_with_product extends Normal {
            Map<String, Object> createdProduct;

            @BeforeEach
            void prepare() throws Exception {
                createdProduct = createToyAndConvert(normalInput());
            }

            @Test
            @DisplayName("상품을 찾아 삭제하고 삭제한 개수와 상태코드 200를 응답한다")
            void It_returns_noContentAndDelete() throws Exception {
                mockMvc.perform(delete("/products/" + createdProduct.get("id")))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$").isNotEmpty())
                        .andExpect(jsonPath("$").value(1));

                mockMvc.perform(get("/products/" + createdProduct.get("id")))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /products/list 요청은")
    class Describe_deleteProducts {
        @Nested
        @DisplayName("제거할 상품 목록이 주어지면")
        class Context_with_idList extends Normal {
            Map<String, List<Object>> idInput() throws Exception {
                List<Object> ids = new ArrayList<>();
                ids.add(createToyAndConvert(normalInput()).get("id"));
                ids.add(createToyAndConvert(normalInput()).get("id"));
                ids.add(createToyAndConvert(normalInput()).get("id"));
                ids.add(-1);
                create(normalInput());
                create(normalInput());

                Map<String, List<Object>> idMaps = new HashMap<>();
                idMaps.put("idList", ids);

                return idMaps;
            }

            @Test
            @DisplayName("상품들을 찾아 삭제한다")
            void It_remove_products() throws Exception {
                mockMvc.perform(delete("/products/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(idInput())))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$").value(3));

                assertThat(productRepository.findAll()).hasSize(2);
            }
        }
    }
}
