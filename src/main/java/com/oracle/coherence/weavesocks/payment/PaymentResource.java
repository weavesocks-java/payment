package com.oracle.coherence.weavesocks.payment;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.tangosol.net.NamedCache;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@ApplicationScoped
@Path("/paymentAuth")
public class PaymentResource {

    @Inject
    private NamedCache<String, Authorization> payments;

    @Inject
    private PaymentService paymentService;

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Authorization authPayment(PaymentRequest paymentRequest) {

        Authorization auth = paymentService.authorize(paymentRequest.getAmount());

        payments.put(paymentRequest.getOrderId(), auth);

        return auth;
    }
}