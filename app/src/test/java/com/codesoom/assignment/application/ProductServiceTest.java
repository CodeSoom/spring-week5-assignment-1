package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductBadRequestException;
import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ProductService 테스트")
class ProductServiceTest {
    private ProductService productService;
    private ProductRepository productRepository;

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
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

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
    @DisplayName("getProducts 메서드는")
    class Describe_getProducts {
        @Nested
        @DisplayName("만약 고양이 장난감 목록이 존재한다면")
        class Context_HasListOfProducts {
            @Test
            @DisplayName("저장되어 있는 고양이 장난감 목록을 리턴한다")
            void itReturnsListOfProducts() {
                given(productRepository.findAll()).willReturn(products);

                List<Product> lists = productService.getProducts();
                assertThat(lists).containsExactly(setupProduct, createdProduct);

                verify(productRepository).findAll();
            }
        }

        @Nested
        @DisplayName("만약 고양이 목록이 존재하지 않는다면")
        class Context_HasNotListOfProduct {
            @Test
            @DisplayName("비어있는 고양이 장난감 목록을 리턴한다")
            void itReturnsEmptyListOfProducts() {
                given(productRepository.findAll()).willReturn(List.of());

                List<Product> products = productService.getProducts();

                assertThat(products).isEmpty();

                verify(productRepository).findAll();
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메서드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감의 아이디가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감을 리턴한다")
            void itReturnsWithExistedProduct() {
                given(productRepository.findById(givenExistedId)).willReturn(Optional.of(setupProduct));

                Product product = productService.getProduct(givenExistedId);
                assertThat(product.getId()).isEqualTo(givenExistedId);

                verify(productRepository).findById(givenExistedId);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않는 고양이 장난감의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("고양이 장난감을 찾을 수 없다는 예외를 던진다")
            void itThrowsProductNotFoundException() {
                assertThatThrownBy(() -> productService.getProduct(givenNotExistedId))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(givenNotExistedId);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메서드는")
    class Describe_class {
        @Nested
        @DisplayName("만약 고양이 장난감 객체가 주어진다면")
        class Content_WithNameAndMakerAndPriceAndImageUrl {
            private ProductData createSource;

            @BeforeEach
            void setUp() {
                createSource = ProductData.builder()
                        .name(CREATED_PRODUCT_NAME)
                        .maker(CREATED_PRODUCT_MAKER)
                        .price(CREATED_PRODUCT_PRICE)
                        .imageUrl(CREATED_PRODUCT_IMAGEURL)
                        .build();
            }

            @Test
            @DisplayName("장난감 고양이 객체를 저장하고 저장된 객체를 리턴한다")
            void itCreatesProductAndReturnsCreatedProduct() {
                given(productRepository.save(any(Product.class))).willReturn(createdProduct);

                Product createdProduct = productService.createProduct(createSource);
                assertThat(createdProduct.getName())
                        .as("객체의 이름은 %s 이어야 한다", createSource.getName())
                        .isEqualTo(createSource.getName());
                assertThat(createdProduct.getMaker())
                        .as("객체의 메이커는 %s 이어야 한다", createSource.getMaker())
                        .isEqualTo(createSource.getMaker());
                assertThat(createdProduct.getPrice())
                        .as("객체의 가격은 %d 이어야 한다", createSource.getPrice())
                        .isEqualTo(createSource.getPrice());
                assertThat(createdProduct.getImageUrl())
                        .as("객체의 이미지는 %s 이어야 한다", createSource.getImageUrl())
                        .isEqualTo(createSource.getImageUrl());

                verify(productRepository).save(any(Product.class));
            }
        }

        @Nested
        @DisplayName("만약 이름이 비어있는 고양이 장난감 객체가 주어진다면")
        class Content_WithCatToyWithOutName {
            private ProductData createSource;

            @BeforeEach
            void setUp() {
                createSource = ProductData.builder()
                        .name("")
                        .maker(CREATED_PRODUCT_MAKER)
                        .price(CREATED_PRODUCT_PRICE)
                        .imageUrl(CREATED_PRODUCT_IMAGEURL)
                        .build();
            }

            @Test
            @DisplayName("요청이 잘못됐다는 메세지를 응답한다")
            void itReturnsBadRequestMessage() {
                assertThatThrownBy(() -> productService.createProduct(createSource))
                        .isInstanceOf(ProductBadRequestException.class);
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메서드는")
    class Describe_update {
        @Nested
        @DisplayName("만약 저징되어 있는 고양이 장난감의 아이디와 업데이트 될 이름, 메이커, 가격, 이미지가 주어진다면")
        class Context_WithExistedIdAndNameAndMakerAndPriceAndImageUrl {
            private final Long givenExistedId = EXISTED_ID;
            private ProductData updatedSource;

            @BeforeEach
            void setUp() {
                updatedSource = ProductData.builder()
                        .name(UPDATED_PRODUCT_NAME)
                        .maker(UPDATED_PRODUCT_MAKER)
                        .price(UPDATED_PRODUCT_PRICE)
                        .imageUrl(UPDATED_PRODUCT_IMAGEURL)
                        .build();
            }

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감 객체를 업데이트하고 해당 객체를 리턴한다")
            void itUpdatesProductAndReturnsUpdatedProduct() {
                given(productRepository.findById(givenExistedId)).willReturn(Optional.of(setupProduct));

                Product updatedProduct = productService.updateProduct(givenExistedId, updatedSource);
                assertThat(updatedProduct.getName())
                        .as("객체의 이름은 %s 이어야 한다", updatedSource.getName())
                        .isEqualTo(updatedSource.getName());
                assertThat(updatedProduct.getMaker())
                        .as("객체의 메이커는 %s 이어야 한다", updatedSource.getMaker())
                        .isEqualTo(updatedSource.getMaker());
                assertThat(updatedProduct.getPrice())
                        .as("객체의 가격은 %d 이어야 한다", updatedSource.getPrice())
                        .isEqualTo(updatedSource.getPrice());
                assertThat(updatedProduct.getImageUrl())
                        .as("객체의 이미지는 %s 이어야 한다", updatedSource.getImageUrl())
                        .isEqualTo(updatedSource.getImageUrl());

                verify(productRepository).findById(givenExistedId);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메서드는")
    class Describe_delete {
        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감의 아이디가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감 객체를 삭제하고 해당 객체를 리턴한다")
            void itDeletesProductAndReturnsDeletedProduct() {
                given(productRepository.findById(givenExistedId)).willReturn(Optional.of(setupProduct));

                Product deletedProduct = productService.deleteProduct(givenExistedId);
                assertThat(deletedProduct.getId())
                        .as("객체의 아이디는 %f 이어야 한다", EXISTED_ID)
                        .isEqualTo(EXISTED_ID);
                assertThat(deletedProduct.getName())
                        .as("객체의 이름은 %s 이어야 한다", SETUP_PRODUCT_NAME)
                        .isEqualTo(SETUP_PRODUCT_NAME);
                assertThat(deletedProduct.getMaker())
                        .as("객체의 메이커는 %s 이어야 한다", SETUP_PRODUCT_MAKER)
                        .isEqualTo(SETUP_PRODUCT_MAKER);
                assertThat(deletedProduct.getPrice())
                        .as("객체의 가격은 %d 이어야 한다", SETUP_PRODUCT_PRICE)
                        .isEqualTo(SETUP_PRODUCT_PRICE);
                assertThat(deletedProduct.getImageUrl())
                        .as("객체의 이미지는 %s 이어야 한다", SETUP_PRODUCT_IMAGEURL)
                        .isEqualTo(SETUP_PRODUCT_IMAGEURL);

                verify(productRepository).delete(setupProduct);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 고양이 장난감의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("고양이 장난감을 찾을 수 없다는 예외를 던진다")
            void itThrowsProductNotFoundException() {
                given(productRepository.findById(givenNotExistedId))
                        .willThrow(ProductNotFoundException.class);

                assertThatThrownBy(() -> productService.deleteProduct(givenNotExistedId))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(givenNotExistedId);
            }
        }
    }
}
