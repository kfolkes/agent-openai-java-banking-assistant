package com.microsoft.openai.samples.assistant.business.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService();
    }

    @Test
    void testGetAccountBalance_ValidAccountId() {
        // Test with existing account ID
        String balance = accountService.getAccountBalance("1000");
        assertEquals("1000.00", balance);
        
        balance = accountService.getAccountBalance("1010");
        assertEquals("2000,40", balance);
        
        balance = accountService.getAccountBalance("1020");
        assertEquals("3000,20", balance);
    }

    @Test
    void testGetAccountBalance_NonExistentAccountId() {
        // Test with non-existent account ID
        String balance = accountService.getAccountBalance("9999");
        assertNull(balance);
    }

    @Test
    void testGetAccountBalance_NullAccountId() {
        // Test with null account ID
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.getAccountBalance(null);
        });
        assertEquals("AccountId is empty or null", exception.getMessage());
    }

    @Test
    void testGetAccountBalance_EmptyAccountId() {
        // Test with empty account ID
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.getAccountBalance("");
        });
        assertEquals("AccountId is empty or null", exception.getMessage());
    }

    @Test
    void testGetAccountBalance_InvalidAccountId() {
        // Test with non-numeric account ID
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.getAccountBalance("abc");
        });
        assertEquals("AccountId is not a valid number", exception.getMessage());
    }
}