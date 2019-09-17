package com.oracle.coherence.weavesocks.payment;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import io.helidon.grpc.core.MarshallerSupplier;
import io.helidon.microprofile.grpc.core.GrpcMarshaller;
import io.helidon.microprofile.grpc.core.RpcService;
import io.helidon.microprofile.grpc.core.Unary;

import com.oracle.coherence.helidon.io.PofMarshaller;
import com.oracle.io.pof.PortableTypeSerializer;
import com.oracle.io.pof.SimplePofContext;
import com.tangosol.net.NamedCache;

import io.grpc.MethodDescriptor;

@RpcService
@GrpcMarshaller("payment")
@ApplicationScoped
public class PaymentService {
    private static final Logger LOGGER = Logger.getLogger(PaymentService.class.getName());

    private final static float PaymentLimit = 105f;

    @Inject
    private NamedCache<String, Authorization> payments;

    @Unary
    public Authorization authorize(PaymentRequest request) {
        LOGGER.log(Level.INFO, "Authorizing request: " + request);

        float   amount     = request.amount;
        boolean authorised = amount > 0 && amount < PaymentLimit;
        String  message    = authorised
                ? "Payment authorised"
                : amount <= 0
                        ? "Invalid payment amount"
                        : "Payment declined: amount exceeds " + String.format("%.2f", PaymentLimit);

        Authorization auth = new Authorization(authorised, message);
        payments.put(request.orderId, auth);

        LOGGER.log(Level.INFO, "Sending authorization: " + auth);
        return auth;
    }

    // ---- inner class: Marshaller -----------------------------------------

    @ApplicationScoped
    @Named("payment")
    public static class Marshaller implements MarshallerSupplier {

        private final MethodDescriptor.Marshaller<?> marshaller;

        @SuppressWarnings("Duplicates")
        public Marshaller() {
            SimplePofContext ctx = new SimplePofContext()
                    .registerPortableType(PaymentRequest.class)
                    .registerPortableType(Authorization.class)
                    .registerPortableType(Address.class)
                    .registerPortableType(Card.class)
                    .registerPortableType(Customer.class);

            marshaller = new PofMarshaller(ctx);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> MethodDescriptor.Marshaller<T> get(Class<T> aClass) {
            return (MethodDescriptor.Marshaller<T>) marshaller;
        }
    }

}