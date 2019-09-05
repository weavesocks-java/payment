package com.oracle.coherence.weavesocks.payment;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultPaymentService implements PaymentService {

    private final static float PaymentLimit = 105f;

    @Override
    public Authorization authorize(float amount) {
        boolean authorised = amount > 0 && amount < PaymentLimit;

        String message = authorised ? "Payment authorised." :
                amount <= 0 ? "Invalid payment amount." :
                        "Payment declined: amount exceeds " + String.format("%.2f", PaymentLimit);

        return new Authorization(authorised, message, null);
    }
}
