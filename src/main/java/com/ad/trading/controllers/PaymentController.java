package com.ad.trading.controllers;

import com.ad.trading.domain.PaymentMethod;
import com.ad.trading.modals.PaymentOrder;
import com.ad.trading.modals.User;
import com.ad.trading.responses.PaymentResponse;
import com.ad.trading.services.PaymentService;
import com.ad.trading.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {
    public final UserService userService;
    public final PaymentService paymentService;

    @Autowired
    public PaymentController(UserService userService, PaymentService paymentService) {
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @PostMapping("/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(@PathVariable PaymentMethod paymentMethod,
                                                   @PathVariable Long amount,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        PaymentResponse paymentResponse;
        PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)) {
            paymentResponse = paymentService.createRazorpayPaymentLink(user, amount);
        } else {
            paymentResponse = paymentService.createStripePaymentLink(user, amount, order.getId());
        }

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }
}
