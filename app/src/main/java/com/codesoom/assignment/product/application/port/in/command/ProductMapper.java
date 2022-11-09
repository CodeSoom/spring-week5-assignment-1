package com.codesoom.assignment.product.application.port.in.command;

import com.codesoom.assignment.product.adapter.in.web.dto.ProductResponse;
import com.codesoom.assignment.product.domain.Product;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, // 빌드 시 구현체 생성 후 빈으로 등록합니다.
        injectionStrategy = InjectionStrategy.CONSTRUCTOR) // 생성자 주입 전략을 따릅니다.
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductCreateRequest productCreateRequest);

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductUpdateRequest productUpdateRequest);

    /**
     * Entity 객체에서 ResponseDto 객체로 매핑합니다.
     */
    ProductResponse entityToResponse(Product entity);

    /**
     * Entity 객체의 리스트에서 ResponseDto 객체의 리스트로 매핑합니다.
     */
    List<ProductResponse> entityListToResponseList(List<Product> entity);
}
