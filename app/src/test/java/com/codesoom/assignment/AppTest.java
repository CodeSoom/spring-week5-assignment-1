package com.codesoom.assignment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppTest {
    private final App app = new App();

    @Test
    void main() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }

    @Test
    void appHasAGreeting() {
        assertNotNull(app.getGreeting(), "app should have a greeting");
        assertEquals(app.getGreeting(), "Hello, world!");
    }
}
