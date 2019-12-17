package ru.marina.payment.creditcard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration("classpath:application-context-test.xml")
class CreditCardDaoTest {
    @Autowired
    private CreditCardDao creditCardDao;

    @Test
    void getCreditCard() {
        final CreditCard creditCard = new CreditCard("ccId4", "7080203010200010", YearMonth.of(2023, 1),
                "226", "BRAD PITT", new BigDecimal("100000.00"));
        assertEquals(creditCard, creditCardDao.getCreditCard("7080203010200010"));
    }

    @Test
    void updateCreditCard() {
        assertEquals(new BigDecimal("85000.00"), creditCardDao.getCreditCard("1234567891011121").getBalance());
        creditCardDao.updateBalance("ccId1", new BigDecimal("105000.00"));

        final CreditCard creditCard = new CreditCard("ccId1", "1234567891011121", YearMonth.of(2022, 10),
                "508", "PETR IVANOV", new BigDecimal("105000.00"));
        assertEquals(creditCard, creditCardDao.getCreditCard("1234567891011121"));
    }
}