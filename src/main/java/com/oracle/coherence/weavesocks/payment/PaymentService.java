package com.oracle.coherence.weavesocks.payment;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.helidon.microprofile.grpc.core.Unary;

import com.tangosol.net.NamedCache;

@ApplicationScoped
public class PaymentService {

    @Inject
    private NamedCache<String, Authorization> payments;

    @Inject
    private AuthorizationService authorizationService;

    @Unary
    public Authorization authorize(PaymentRequest request) {
        Authorization auth = authorizationService.authorize(request.getAmount());
        payments.put(request.getOrderId(), auth);

        return auth;
    }
}