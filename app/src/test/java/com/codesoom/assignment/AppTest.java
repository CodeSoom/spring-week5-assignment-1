package com.codesoom.assignment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AppTest {
    @Test
    @DisplayName("애플리케이션은 정상적으로 작동한다")
    void appWorksFine() {
        App app = new App();

       assertNotNull(app.getGreeting());
    }
}
