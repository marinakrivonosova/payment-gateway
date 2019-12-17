package ru.marina.payment.creditcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditCardController {
    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardController(final CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping("/pay")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResult processPayment(@RequestBody final ProcessPaymentRequest request) {
        creditCardService.processPayment(request.getCardNumber(), request.getCvc(), request.getExpirationDate(), request.getCardHolder(), request.getChargedAmount());
        return new PaymentResult("token");
    }
}
