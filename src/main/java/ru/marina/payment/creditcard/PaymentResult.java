package ru.marina.payment.creditcard;

public class PaymentResult {
    private String paymentReference;

    public PaymentResult() {
    }

    public PaymentResult(final String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(final String paymentReference) {
        this.paymentReference = paymentReference;
    }
}
