package com.codesoom.assignment.product.controller;

import com.codesoom.assignment.product.application.ProductCommand;
import com.codesoom.assignment.product.controller.ProductDto.RequestParam;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapperImpl implements ProductDtoMapper {
    public ProductCommand.Register of(RequestParam request) {
        if (request == null) {
            return null;
        }

        return ProductCommand.Register.builder()
                .name(request.getName())
                .maker(request.getMaker())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .build();
    }

    public ProductCommand.UpdateRequest of(Long id, RequestParam requestParam) {
        if (id == null || requestParam == null) {
            return null;
        }

        return ProductCommand.UpdateRequest.builder()
                .id(id)
                .name(requestParam.getName())
                .maker(requestParam.getMaker())
                .price(requestParam.getPrice())
                .imageUrl(requestParam.getImageUrl())
                .build();
    }
}
