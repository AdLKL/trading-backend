package com.ad.trading.responses;

import lombok.Data;

@Data
public class PaymentResponse {
    private String paymentUrl;
    private String paymentId;
}
