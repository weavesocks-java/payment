package com.oracle.coherence.weavesocks.payment;

/**
 * A service that authorize a payment.
 */
public interface AuthorizationService {

    public Authorization authorize(float amount);
}