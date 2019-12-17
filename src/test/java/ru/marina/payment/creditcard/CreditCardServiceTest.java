package ru.marina.payment.creditcard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.marina.payment.exceptions.NotEnoughMoneyException;
import ru.marina.payment.exceptions.PaymentDeniedException;

import java.math.BigDecimal;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration("classpath:application-context-test.xml")
@WebAppConfiguration
class CreditCardServiceTest {
    @Autowired
    private CreditCardDao creditCardDao;
    @Autowired
    private CreditCardService creditCardService;

    @Test
    void processPayment() {
        creditCardService.processPayment("1234123412341234", "437", YearMonth.of(2023, 7), "JOHN SNOW", new BigDecimal("1000.00"));

        assertEquals(new BigDecimal("3260.80"), creditCardDao.getCreditCard("1234123412341234").getBalance());
    }

    @Test
    void cardExistenceViolation() {
        assertThrows(EmptyResultDataAccessException.class, () -> creditCardService.processPayment(
                "1234500891011121",
                "508", YearMonth.of(2022,10),
                "PETR IVANOV",
                new BigDecimal("50000.00"))
        );
    }

    @Test
    void moneyAmountExistenceViolation() {
        assertThrows(NotEnoughMoneyException.class, () -> creditCardService.processPayment(
                "1234567891011121",
                "508", YearMonth.of(2022,10),
                "PETR IVANOV",
                new BigDecimal("500000.00"))
        );
        assertEquals(new BigDecimal("85000.00"), creditCardDao.getCreditCard("1234567891011121").getBalance());
    }

    @Test
    void cardDataViolation() {
        assertThrows(PaymentDeniedException.class, () -> creditCardService.processPayment(
                "1234567891011121",
                "108", YearMonth.of(2022,10),
                "PETR IVANOV",
                new BigDecimal("5000.00"))
        );
    }

    @Test
    void testExceptionPriority() {
        assertThrows(NotEnoughMoneyException.class, () -> creditCardService.processPayment(
                "1234567891011121",
                "108", YearMonth.of(2022,10),
                "PETR IVANOV",
                new BigDecimal("500000.00"))
        );
        assertEquals(new BigDecimal("85000.00"), creditCardDao.getCreditCard("1234567891011121").getBalance());

        assertThrows(PaymentDeniedException.class, () -> creditCardService.processPayment(
                "1234567891011121",
                "118", YearMonth.of(2022,10),
                "PETR IVANOV",
                new BigDecimal("5000.00"))
        );
        assertThrows(EmptyResultDataAccessException.class, () -> creditCardService.processPayment(
                "0034567891011121",
                "108", YearMonth.of(2022,10),
                "PETR IVANOV",
                new BigDecimal("500000.00"))
        );
        assertEquals(new BigDecimal("85000.00"), creditCardDao.getCreditCard("1234567891011121").getBalance());
    }
}