package ru.marina.payment.exceptions;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(final String errorMessage){
        super(errorMessage);
    }
}
