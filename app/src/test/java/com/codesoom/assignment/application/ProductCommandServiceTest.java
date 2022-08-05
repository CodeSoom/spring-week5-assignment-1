package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.domain.Status;
import com.codesoom.assignment.dto.ListToDelete;
import com.codesoom.assignment.dto.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ProductCommand 인터페이스의")
public class ProductCommandServiceTest {
    public static final String NAME = "먼지털이";
    public static final String MAKER = "코드숨";
    public static final int PRICE = 2200000;
    public static final String URL = "picture.com";
    public static final Long BASIC_ID = 1L;
    public static final String SALE = "SALE";

    private ProductCommandService commandService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        commandService = new ToyCommandService(productRepository);
    }

    private ProductData givenProductData() {
        return new ProductData(NAME, MAKER, PRICE, URL, SALE);
    }

    private Product createdProduct() {
        return new Product(BASIC_ID, NAME, MAKER, PRICE, URL, Status.SALE);
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {
        @Nested
        @DisplayName("상품 정보가 주어지면")
        class Context_with_product {
            ProductData productData = new ProductData(NAME, MAKER, PRICE, URL, SALE);
            Product expect = createdProduct();

            @BeforeEach
            void prepare() {
                given(productRepository.save(productData.toProduct()))
                        .willReturn(expect);
            }

            @Test
            @DisplayName("상품을 리턴한다")
            void It_returns_product() {
                assertThat(commandService.create(productData)).isEqualTo(expect);

                verify(productRepository).save(any(Product.class));
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {
        @Nested
        @DisplayName("찾는 상품이 있다면")
        class Context_with_product {
            @BeforeEach
            void prepare() {
                given(productRepository.save(givenProductData().toProduct()))
                        .willReturn(createdProduct());
            }

            @Test
            @DisplayName("상품을 삭제한다")
            void It_remove_product() {
                commandService.deleteById(BASIC_ID);

                verify(productRepository).deleteById(BASIC_ID);
            }
        }
    }

    @Nested
    @DisplayName("deleteByList 메서드는")
    class Describe_deleteByList {
        @Nested
        @DisplayName("삭제할 상품 목록이 주어지면")
        class Context_with_listToDelete {
            @BeforeEach
            void prepare() {
                for (int i = 0; i < 5; i++) {
                    given(productRepository.save(givenProductData().toProduct()))
                            .willReturn(createdProduct());
                }
            }

            ListToDelete givenList() {
                return new ListToDelete(Arrays.asList(1L, 2L, 3L, 4L));
            }

            @Test
            @DisplayName("상품들을 제거한다")
            void It_remove_products() {
                commandService.deleteAllByList(givenList());
            }
        }
    }
}
