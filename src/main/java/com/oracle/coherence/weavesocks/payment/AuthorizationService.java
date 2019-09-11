package com.oracle.coherence.weavesocks.payment;

/**
 * A service that authorize a payment.
 */
public interface AuthorizationService {
    Authorization authorize(float amount);
}