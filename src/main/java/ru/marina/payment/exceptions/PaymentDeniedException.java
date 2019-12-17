package ru.marina.payment.exceptions;

public class PaymentDeniedException extends RuntimeException {
    public PaymentDeniedException(final String errorMessage){
        super(errorMessage);
    }
}
