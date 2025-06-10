package com.microsoft.openai.samples.assistant.business.controller;

import com.microsoft.openai.samples.assistant.business.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void testGetAccountBalance_ValidAccountId() throws Exception {
        // Mock the service to return a balance
        when(accountService.getAccountBalance("1000")).thenReturn("1000.00");

        // Perform the GET request and validate the response
        mockMvc.perform(get("/accounts/1000/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000.00"));
    }

    @Test
    void testGetAccountBalance_NonExistentAccountId() throws Exception {
        // Mock the service to return null for non-existent account
        when(accountService.getAccountBalance("9999")).thenReturn(null);

        // Perform the GET request and validate the response
        mockMvc.perform(get("/accounts/9999/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void testGetAccountBalance_EuroFormat() throws Exception {
        // Test with European number format (comma as decimal separator)
        when(accountService.getAccountBalance("1010")).thenReturn("2000,40");

        mockMvc.perform(get("/accounts/1010/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("2000,40"));
    }
}