package ru.marina.payment.creditcard;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Objects;

public class CreditCard {
    private String id;
    private String cardNumber;
    @JsonDeserialize(using = YearMonthDeserializer.class)
    private YearMonth expirationDate;
    private String cvc;
    private String cardHolder;
    @JsonDeserialize(using = NumberDeserializers.BigDecimalDeserializer.class)
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal balance;

    public CreditCard() {
    }

    public CreditCard(final String id, final String cardNumber, final YearMonth expirationDate, final String cvc, final String cardHolder, final BigDecimal balance) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvc = cvc;
        this.cardHolder = cardHolder;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public YearMonth getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(final YearMonth expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(final String cvc) {
        this.cvc = cvc;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(final String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id='" + id + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", cvc='" + cvc + '\'' +
                ", cardHolder='" + cardHolder + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreditCard that = (CreditCard) o;
        return id.equals(that.id) &&
                cardNumber.equals(that.cardNumber) &&
                expirationDate.equals(that.expirationDate) &&
                cvc.equals(that.cvc) &&
                cardHolder.equals(that.cardHolder) &&
                balance.equals(that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, expirationDate, cvc, cardHolder, balance);
    }
}
