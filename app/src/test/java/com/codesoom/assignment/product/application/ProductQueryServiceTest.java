package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.application.ProductQueryService;
import com.codesoom.assignment.product.application.ToyQueryService;
import com.codesoom.assignment.product.dto.ProductNotFoundException;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.ProductRepository;
import com.codesoom.assignment.product.domain.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ProductQueryService 인터페이스의")
public class ProductQueryServiceTest {
    public static final Long BASIC_ID = 1L;
    public static final String NAME = "장난감명";
    public static final String MAKER = "메이커명";
    public static final Integer PRICE = 3000;
    public static final String URL = "image.url";
    public static final Long NOT_EXIST_ID = -1L;

    private ProductQueryService queryService;
    private ProductRepository productRepository;

    private Product createProduct() {
        return new Product(BASIC_ID, NAME, MAKER, PRICE, URL);
    }

    private Product createProduct(Status status) {
        return new Product(BASIC_ID, NAME, MAKER, PRICE, URL, status);
    }

    private List<Product> createProductList() {
        List<Product> productList = new ArrayList<>();

        productList.add(createProduct(Status.SALE));
        productList.add(createProduct(Status.SALE));
        productList.add(createProduct(Status.SOLD_OUT));
        productList.add(createProduct(Status.SOLD_OUT));
        productList.add(createProduct(Status.SOLD_OUT));

        return productList;
    }

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        queryService = new ToyQueryService(productRepository);
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {
        @Nested
        @DisplayName("상품이 있다면")
        class Context_with_product {
            @BeforeEach
            void prepare() {
                given(productRepository.findById(BASIC_ID))
                        .willReturn(Optional.of(createProduct()));
            }

            @Test
            @DisplayName("상품을 리턴한다")
            void It_returns_product() {
                assertThat(queryService.findById(BASIC_ID))
                        .isEqualTo(createProduct());

                verify(productRepository).findById(BASIC_ID);
            }
        }

        @Nested
        @DisplayName("상품이 없다면")
        class Context_without_product {
            @BeforeEach
            void prepare() {
                given(productRepository.findById(NOT_EXIST_ID))
                        .willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 던진다")
            void It_throws_exception() {
                assertThatThrownBy(() -> queryService.findById(NOT_EXIST_ID))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {
        @Nested
        @DisplayName("상품 목록이 주어지면")
        class Context_with_productList {
            @BeforeEach
            void prepare() {
                given(productRepository.findAll())
                        .willReturn(createProductList());
            }

            @Test
            @DisplayName("판매중인 상품의 목록을 리턴한다")
            void It_returns_productList() {
                assertThat(queryService.findAll())
                        .hasSize(2);

                verify(productRepository).findAll();
            }
        }
    }

    @Nested
    @DisplayName("findAllSoldOut 메서드는")
    class Describe_findAllSoldOut {
        @Nested
        @DisplayName("상품 목록이 주어지면")
        class Context_with_productList {
            @BeforeEach
            void prepare() {
                given(productRepository.findAll())
                        .willReturn(createProductList());
            }

            @Test
            @DisplayName("품절된 상품을 리턴한다")
            void It_returns_soldOutProduct() {
                assertThat(queryService.findAllSoldOut())
                        .hasSize(3);

                verify(productRepository).findAll();
            }
        }
    }
}
