package com.codesoom.assignment.controller.product;

import com.codesoom.assignment.application.product.ProductCommand;
import com.codesoom.assignment.common.ProductSampleFactory;
import com.codesoom.assignment.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
@DisplayName("ProductDtoMapper 클래스")
class ProductDtoMapperTest {

    @Nested
    @DisplayName("of(RequestParam) 메소드는")
    class Describe_of_request_param {
        @Nested
        @DisplayName("유효한 요청 파라미터가 주어지면")
        class Context_with_valid_request_parameter {
            @Test
            @DisplayName("Register 객체를 리턴한다")
            void it_returns_register() {
                final Product product = ProductSampleFactory.createProduct(1L);

                final ProductCommand.Register actual = ProductSampleFactory.of(product);

                assertThat(actual).isInstanceOf(ProductCommand.Register.class);
            }
        }

        @Nested
        @DisplayName("요청 파라미터가 Null이면")
        class Context_with_invalid_request_parameter {
            @Test
            @DisplayName("Null을 리턴한다")
            void it_returns_register() {
                final ProductDto.RequestParam request = null;

                final ProductCommand.Register actual = ProductDtoMapper.INSTANCE.of(request);

                assertThat(actual).isNull();
            }
        }

    }

    @Nested
    @DisplayName("of(id, RequestParam) 메소드는")
    class Describe_of_id_and_request_param {
        @Nested
        @DisplayName("유효한 요청 파라미터가 주어지면")
        class Context_with_valid_request_parameter {
            @Test
            @DisplayName("UpdateReq 객체를 리턴한다")
            void it_returns_register() {
                final Long id = 1L;

                final ProductCommand.UpdateRequest actual = ProductDtoMapper.INSTANCE.of(id, ProductSampleFactory.createRequestParam());

                assertThat(actual).isInstanceOf(ProductCommand.UpdateRequest.class);
            }
        }

        @Nested
        @DisplayName("모든 파라미터가 Null이면")
        class Context_with_invalid_request_parameter {
            @Test
            @DisplayName("Null을 리턴한다")
            void it_returns_null() {
                final Long id = null;
                final ProductDto.RequestParam request = null;

                final ProductCommand.UpdateRequest actual = ProductDtoMapper.INSTANCE.of(id, request);

                assertThat(actual).isNull();
            }
        }

        @Nested
        @DisplayName("ID가 Null이면")
        class Context_with_id_null {
            @Test
            @DisplayName("ID필드가 Null인 객체를 리턴한다")
            void it_returns_null() {
                final Long id = null;

                final ProductCommand.UpdateRequest actual = ProductDtoMapper.INSTANCE.of(id, ProductSampleFactory.createRequestParam());
                System.out.println(actual);

                assertThat(actual.getId()).isNull();
            }
        }

        @Nested
        @DisplayName("RequestParam이 Null이면")
        class Context_with_requestparam_null {
            @Test
            @DisplayName("RequestParam의 필드들이 Null인 객체를 리턴한다")
            void it_returns_null() {
                final Long id = 1L;
                final ProductDto.RequestParam request = null;

                final ProductCommand.UpdateRequest actual = ProductDtoMapper.INSTANCE.of(id, request);

                assertThat(actual.getName()).isNull();
                assertThat(actual.getMaker()).isNull();
                assertThat(actual.getPrice()).isNull();
            }
        }
    }
}
