package com.ad.trading.services;

import com.ad.trading.domain.PaymentMethod;
import com.ad.trading.modals.PaymentOrder;
import com.ad.trading.modals.User;
import com.ad.trading.responses.PaymentResponse;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrderById(Long id) throws Exception;
    Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;
    PaymentResponse createRazorpayPaymentLink(User user, Long amount) throws RazorpayException;
    PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;

}
