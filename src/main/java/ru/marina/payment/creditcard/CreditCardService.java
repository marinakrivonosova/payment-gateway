package ru.marina.payment.creditcard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.marina.payment.exceptions.NotEnoughMoneyException;
import ru.marina.payment.exceptions.PaymentDeniedException;

import java.math.BigDecimal;
import java.time.YearMonth;

@Service
public class CreditCardService {
    private static final Logger logger = LoggerFactory.getLogger(CreditCardService.class);
    private final CreditCardDao creditCardDao;

    @Autowired
    public CreditCardService(final CreditCardDao creditCardDao) {
        this.creditCardDao = creditCardDao;
    }

    @Transactional
    public void processPayment(final String cardNumber, final String cvc, final YearMonth expirationDate,
                               final String cardHolder, final BigDecimal chargedAmount) {
        logger.info("Starting processing payment");
        final CreditCard creditCard = creditCardDao.getCreditCard(cardNumber);
        if (creditCard.getBalance().compareTo(chargedAmount) < 0) {
            throw new NotEnoughMoneyException("Payment denied: not enough money.");
        }
        if (creditCard.getCardNumber().equals(cardNumber)
                && creditCard.getCvc().equals(cvc)
                && creditCard.getCardHolder().toLowerCase().equals(cardHolder.toLowerCase())
                && creditCard.getExpirationDate().equals(expirationDate)) {
            creditCardDao.updateBalance(creditCard.getId(), creditCard.getBalance().subtract(chargedAmount));
            logger.info("Payment successfully processed");
        } else {
            throw new PaymentDeniedException("Payment failed");
        }
    }
}
