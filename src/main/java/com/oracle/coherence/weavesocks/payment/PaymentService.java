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

    @Inject
    private NamedCache<String, Authorization> payments;

    @Inject
    private AuthorizationService authorizationService;

    @Unary
    public Authorization authorize(PaymentRequest request) {
        LOGGER.log(Level.INFO, "Authorizing request: " + request);
        Authorization auth = authorizationService.authorize(request.getAmount());
        payments.put(request.getOrderId(), auth);

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
            SimplePofContext ctx = new SimplePofContext();
            ctx.registerUserType(1, PaymentRequest.class, new PortableTypeSerializer(1, PaymentRequest.class));
            ctx.registerUserType(2, Authorization.class, new PortableTypeSerializer(2, Authorization.class));
            ctx.registerUserType(3, Address.class, new PortableTypeSerializer(3, Address.class));
            ctx.registerUserType(4, Card.class, new PortableTypeSerializer(4, Card.class));
            ctx.registerUserType(5, Customer.class, new PortableTypeSerializer(5, Customer.class));

            marshaller = new PofMarshaller(ctx);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> MethodDescriptor.Marshaller<T> get(Class<T> aClass) {
            return (MethodDescriptor.Marshaller<T>) marshaller;
        }
    }

}