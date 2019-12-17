package ru.marina.payment.creditcard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreditCardControllerTest {
    private MockMvc mockMvc;

    private CreditCardService creditCardService = mock(CreditCardService.class);

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CreditCardController(creditCardService)).build();
    }

    @Test
    void processPayment() throws Exception {
        try (final InputStream inputStream = new ClassPathResource("process-payment.json").getInputStream()) {
            mockMvc.perform(post("/pay")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(inputStream.readAllBytes())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }
    }
}