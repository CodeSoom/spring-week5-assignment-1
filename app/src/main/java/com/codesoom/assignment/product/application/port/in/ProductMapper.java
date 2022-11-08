package com.codesoom.assignment.product.application.port.in;

import com.codesoom.assignment.product.adapter.in.web.dto.ProductRequest;
import com.codesoom.assignment.product.adapter.in.web.dto.ProductResponse;
import com.codesoom.assignment.product.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    /**
     * RequestDto 객체에서 Command 객체로 매핑합니다.
     */
    ProductCommand requestToCommand(ProductRequest productRequest);

    /**
     * Command 객체에서 Entity 객체로 매핑합니다.
     */
    @Mapping(target = "id", ignore = true)
    Product commandToEntity(ProductCommand productCommand);

    /**
     * Entity 객체에서 ResponseDto 객체로 매핑합니다.
     */
    ProductResponse entityToResponse(Product entity);

    /**
     * Entity 객체의 리스트에서 ResponseDto 객체의 리스트로 매핑합니다.
     */
    List<ProductResponse> entityListToResponseList(List<Product> entity);
}
