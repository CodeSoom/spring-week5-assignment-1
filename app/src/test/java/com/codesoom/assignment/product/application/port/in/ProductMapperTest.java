package com.codesoom.assignment.product.application.port.in;

import com.codesoom.assignment.product.adapter.in.web.dto.ProductRequest;
import com.codesoom.assignment.product.adapter.in.web.dto.ProductResponse;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.support.ProductFixture;
import com.codesoom.assignment.support.IdFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {
    @Test
    @DisplayName("Request to Command 변환 테스트")
    void convertRequestToCommand() {
        ProductRequest productRequest = ProductFixture.TOY_1.요청_데이터_생성();

        ProductCommand productCommand = productRequest.toCommand();

        assertThat(productCommand.getName()).isEqualTo(productRequest.getName());
        assertThat(productCommand.getMaker()).isEqualTo(productRequest.getMaker());
        assertThat(productCommand.getPrice()).isEqualTo(productRequest.getPrice());
    }

    @Test
    @DisplayName("Command to Entity 변환 테스트")
    void convertCommandToEntity() {
        ProductCommand productCommand = ProductFixture.TOY_1.커맨드_데이터_생성();

        Product product = productCommand.toEntity();

        assertThat(product.getName()).isEqualTo(productCommand.getName());
        assertThat(product.getMaker()).isEqualTo(productCommand.getMaker());
        assertThat(product.getPrice()).isEqualTo(productCommand.getPrice());
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
