package com.codesoom.assignment;

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

import java.nio.charset.StandardCharsets;

/**
 * MockMvc 테스트 결과값의 CharacterEncoding을 UTF-8로 설정합니다.
 * 기본적으로 @AutoconfigureMockMvc를 통해 주입받는 상황에서만 적용됩니다.
 */
@Component
class MockMvcCharacterEncodingCustomizer implements MockMvcBuilderCustomizer {
    @Override
    public void customize(ConfigurableMockMvcBuilder<?> builder) {
        builder.alwaysDo(result -> result.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name()));
    }
}
