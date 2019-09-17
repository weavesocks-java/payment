package com.oracle.coherence.weavesocks.payment;

import com.oracle.io.pof.annotation.Portable;
import com.oracle.io.pof.annotation.PortableType;

@PortableType(id = 1)
public class PaymentRequest {
    @Portable String orderId;
    @Portable Address address;
    @Portable Card card;
    @Portable Customer customer;
    @Portable float amount;

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "orderId=" + orderId +
                ", address=" + address +
                ", card=" + card +
                ", customer=" + customer +
                ", amount=" + amount +
                '}';
    }
}
