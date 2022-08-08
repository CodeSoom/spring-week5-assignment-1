package com.codesoom.assignment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AppTest {
    @Autowired
    private App app;

    @Test
    @DisplayName("애플리케이션은 정상적으로 작동한다")
    void appWorksFine() {
       assertNotNull(app);
    }
}
