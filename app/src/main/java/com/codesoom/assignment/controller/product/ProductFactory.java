package com.codesoom.assignment.controller.product;

import com.codesoom.assignment.application.product.ProductCommand;
import com.codesoom.assignment.controller.product.ProductDto.RequestParam;

public class ProductFactory {
    public static ProductCommand.Register of(RequestParam request) {
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

    public static ProductCommand.UpdateRequest of(Long id, RequestParam requestParam) {
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
