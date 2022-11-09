package com.codesoom.assignment.product.application.port.in.command;

import com.codesoom.assignment.product.adapter.in.web.dto.ProductCreateRequestDto;
import com.codesoom.assignment.product.adapter.in.web.dto.ProductResponse;
import com.codesoom.assignment.product.adapter.in.web.dto.ProductUpdateRequestDto;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.support.IdFixture;
import com.codesoom.assignment.support.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {
    @Test
    @DisplayName("Create Request to Entity 변환 테스트")
    void createRequestToEntity() {
        ProductCreateRequestDto productCreateRequestDto = ProductFixture.TOY_1.생성_요청_데이터_생성();

        Product product = ProductMapper.INSTANCE.toEntity(productCreateRequestDto);

        assertThat(product.getName()).isEqualTo(productCreateRequestDto.getName());
        assertThat(product.getMaker()).isEqualTo(productCreateRequestDto.getMaker());
        assertThat(product.getPrice()).isEqualTo(productCreateRequestDto.getPrice());
        assertThat(product.getImageUrl()).isEqualTo(productCreateRequestDto.getImageUrl());
    }


    @Test
    @DisplayName("Update Request to Entity 변환 테스트")
    void updateRequestToEntity() {
        ProductUpdateRequestDto productCreateRequestDto = ProductFixture.TOY_1.수정_요청_데이터_생성();

        Product product = ProductMapper.INSTANCE.toEntity(productCreateRequestDto);

        assertThat(product.getName()).isEqualTo(productCreateRequestDto.getName());
        assertThat(product.getMaker()).isEqualTo(productCreateRequestDto.getMaker());
        assertThat(product.getPrice()).isEqualTo(productCreateRequestDto.getPrice());
        assertThat(product.getImageUrl()).isEqualTo(productCreateRequestDto.getImageUrl());
    }

    @Test
    @DisplayName("Entity to Response 변환 테스트")
    void convertEntityToResponse() {
        Product product = ProductFixture.TOY_1.엔티티_생성(IdFixture.ID_MIN.value());

        ProductResponse productResponse = ProductResponse.from(product);

        assertThat(productResponse.getName()).isEqualTo(product.getName());
        assertThat(productResponse.getMaker()).isEqualTo(product.getMaker());
        assertThat(productResponse.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    @DisplayName("Entity to Response 변환 테스트")
    void convertEntityListToResponseList() {
        List<Product> productList = new ArrayList<>();
        productList.add(ProductFixture.TOY_1.엔티티_생성());
        productList.add(ProductFixture.TOY_2.엔티티_생성());
        productList.add(ProductFixture.TOY_3.엔티티_생성());
        productList.add(null);

        List<ProductResponse> productResponses = ProductResponse.fromList(productList);

        assertThat(productResponses).hasSize(productList.size());
    }
}
