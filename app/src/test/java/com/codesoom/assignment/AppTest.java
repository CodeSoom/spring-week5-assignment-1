package com.codesoom.assignment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {
    @Test
    void main() {
        App.main(new String[] {});
    }

    @Test
    void appHasAGreeting() {
        App app = new App();
        assertThat(app.getGreeting()).isEqualTo("Hello, world!");
    }
}
