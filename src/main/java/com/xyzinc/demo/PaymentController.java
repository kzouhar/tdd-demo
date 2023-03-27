package com.xyzinc.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class PaymentController {

    @PostMapping(path = "/v1/payment")
    public CompletableFuture<ResponseEntity<MakePaymentResponse>> makePayment(
            @RequestHeader(name = "transaction-id") String transactionId,
            @RequestBody MakePaymentRequest paymentRequest
            ) {
        return CompletableFuture.completedFuture(ResponseEntity.ok(new MakePaymentResponse("1234")));
    }

}
