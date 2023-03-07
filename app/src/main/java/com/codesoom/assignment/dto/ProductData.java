package com.codesoom.assignment.dto;

import com.github.dozermapper.core.Mapping;
import lombok.*;

import javax.annotation.MatchesPattern;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductData {
    
    private Long id;

    @Mapping("name")
    @NotBlank(message = "제품명을 입력하지 않았습니다. 제품명을 입력해주세요.")
    private String name;

    @Mapping("maker")
    @NotBlank(message = "메이커를 입력하지 않았습니다. 메이커를 입력해주세요.")
    private String maker;

    @Mapping("price")
    @NotNull(message = "가격을 입력하지 않았습니다. 가격을 입력해주세요.")
    @Min(value = 0, message = "가격을 잘못 입력하셨습니다. 0 이상의 양수만 입력해주세요.") // 양수와 0만 허용
    private Integer price;

    @Mapping("imageUrl")
    private String imageUrl;
}
