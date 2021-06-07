package com.codesoom.assignment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("App 클래스의")
class AppTest {

    @Test
    @DisplayName("main 메서드는 스프링 애플리케이션을 실행한다")
    void springApplicationContextTest() {
        String[] args = new String[]{
                "--spring.main.banner-mode=off",
                "--logging.level.root=ERROR"
        };
        App.main(args);
    }

    @Test
    @DisplayName("getGreeting 메서드는 인사말을 반환한다")
    void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
        assertEquals(classUnderTest.getGreeting(), "Hello, world!");
    }

}
