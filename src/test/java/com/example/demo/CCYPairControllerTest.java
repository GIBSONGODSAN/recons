package com.example.demo;

import com.example.demo.controller.CCYPairController;
import com.example.demo.fxrates.CurrencyPairGenerator;
import com.example.demo.handler.ResponseHandler;
import com.example.demo.model.CurrencyPairTypeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CCYPairControllerTest {

    @Mock
    private CurrencyPairGenerator currencyPairGenerator;

    @InjectMocks
    private CCYPairController ccyPairController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ccyPairController).build();
    }

    @Test
    public void testGetCurrencyPairsByType() {
        // Prepare test data
        CurrencyPairTypeRequest request = new CurrencyPairTypeRequest(null, null);
        request.setType("FX");
        request.setUsername("testUser");

        List<String> mockPairs = Arrays.asList("EUR/USD", "GBP/USD");
        when(currencyPairGenerator.getPairsByType("FX")).thenReturn(mockPairs);

        // Call the method to be tested
        ResponseEntity<Object> responseEntity = ccyPairController.getCurrencyPairsByType(request);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // assertEquals("Currency Pairs by Type", responseEntity.getBody().get("message"));
        // assertEquals(mockPairs, ((List<String>) responseEntity.getBody()).get("pairs"));
    }

    @Test
    public void testGetCurrencyPairsByType_EmptyType() {
        // Prepare test data
        CurrencyPairTypeRequest request = new CurrencyPairTypeRequest(null, null);
        request.setType("");
        request.setUsername("testUser");

        // Call the method to be tested
        ResponseEntity<Object> responseEntity = ccyPairController.getCurrencyPairsByType(request);

        // Verify the response
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // assertEquals("Currency Pair Type cannot be empty", responseEntity.getBody().get("message"));
    }

    @Test
    public void testGetCurrencyPairsByType_EmptyUsername() {
        // Prepare test data
        CurrencyPairTypeRequest request = new CurrencyPairTypeRequest(null, null);
        request.setType("FX");
        request.setUsername("");

        // Call the method to be tested
        ResponseEntity<Object> responseEntity = ccyPairController.getCurrencyPairsByType(request);

        // Verify the response
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // assertEquals("Username cannot be empty", responseEntity.getBody().get("message"));
    }
}
