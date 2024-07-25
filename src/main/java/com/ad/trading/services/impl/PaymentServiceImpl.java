package com.ad.trading.services.impl;

import com.ad.trading.domain.PaymentMethod;
import com.ad.trading.domain.PaymentOrderStatus;
import com.ad.trading.modals.PaymentOrder;
import com.ad.trading.modals.User;
import com.ad.trading.repositories.PaymentOrderRepository;
import com.ad.trading.responses.PaymentResponse;
import com.ad.trading.services.PaymentService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentOrderRepository paymentOrderRepository;
    @Value("${stripe.api.key}")
    private String stripeSecretKey;
    @Value("${razorpay.api.key}")
    private String apiKey;
    @Value("${razorpay.api.secret}")
    private String apiSecretKey;

    @Autowired
    public PaymentServiceImpl(PaymentOrderRepository paymentOrderRepository) {
        this.paymentOrderRepository = paymentOrderRepository;
    }

    @Override
    public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod) {
        PaymentOrder order = new PaymentOrder();
        order.setAmount(amount);
        order.setPaymentMethod(paymentMethod);
        order.setUser(user);
        return paymentOrderRepository.save(order);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        return paymentOrderRepository.findById(id).orElseThrow(() -> new Exception("Payment not found"));
    }

    @Override
    public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {
        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {
            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)) {
                RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecretKey);
                Payment payment = razorpay.payments.fetch(paymentId);
                Integer amount = payment.get("amount");
                String status = payment.get("status");

                if(status.equals("captured")) {
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    return true;
                }
                paymentOrder.setStatus(PaymentOrderStatus.FAILED);
                paymentOrderRepository.save(paymentOrder);
                return false;
            }
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderRepository.save(paymentOrder);
            return true;
        }
        return false;
    }

    @Override
    public PaymentResponse createRazorpayPaymentLink(User user, Long amount) throws RazorpayException {
        Long amountCents = amount * 100;
        try {
            // Instanciate a Razorpay client with your key Id and Secret
            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecretKey);

            // Create a JSON object with the payment link request parameters
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");

            // Create a JSON object with the customer details
            JSONObject customer = new JSONObject();
            customer.put("name", user.getFullName());
            customer.put("email", user.getEmail());
            paymentLinkRequest.put("customer", customer);

            // Create a JSON object with the notification settings
            JSONObject notify = new JSONObject();
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            // Set the reminder settings
            paymentLinkRequest.put("reminder_enable", true);

            // Set the callback URL and method
            paymentLinkRequest.put("callback_url", "http://localhost:5173/wallet");
            paymentLinkRequest.put("callback_method", "get");

            // Create the payment link using the paymentLink.create() method
            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentResponse res = new PaymentResponse();
            res.setPaymentUrl(paymentLinkUrl);

            return res;
        } catch(RazorpayException e) {
            System.out.println("Error creating payment link: " + e.getMessage());
            throw new RazorpayException("Error creating payment link: " + e.getMessage());
        }
    }

    @Override
    public PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/wallet?order_id=" + orderId)
                .setCancelUrl("http://localhost:5173/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount*100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder()
                                        .setName("Top up wallet")
                                        .build()
                                ).build()
                        ).build()
                ).build();
        Session session = Session.create(params);

        PaymentResponse res = new PaymentResponse();
        res.setPaymentUrl(session.getUrl());

        return res;
    }
}
