package com.xyzinc.demo;

public class MakePaymentResponse {
    String reference;
    public MakePaymentResponse(String reference) {
        this.reference = reference;
    }
    public String getReference() {
        return reference;
    }

}
