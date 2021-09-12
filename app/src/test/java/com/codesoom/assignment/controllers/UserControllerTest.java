package com.codesoom.assignment.controllers;

import jdk.internal.jshell.tool.ConsoleIOContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {
    private MockMvc mockMvc;
    
    @Test
    void registerUser() {
        mockMvc.perform();
    }
}