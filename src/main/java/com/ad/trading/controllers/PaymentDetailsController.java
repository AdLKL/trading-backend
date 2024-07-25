package com.ad.trading.controllers;

import com.ad.trading.modals.PaymentDetails;
import com.ad.trading.modals.User;
import com.ad.trading.services.PaymentDetailsService;
import com.ad.trading.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment-details")
public class PaymentDetailsController {
    private final UserService userService;
    private final PaymentDetailsService paymentDetailsService;

    @Autowired
    public PaymentDetailsController(UserService userService, PaymentDetailsService paymentDetailsService) {
        this.userService = userService;
        this.paymentDetailsService = paymentDetailsService;
    }

    @PostMapping
    public ResponseEntity<PaymentDetails> addPaymentDetails(@RequestBody PaymentDetails paymentDetailsRequest,
                                                            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentDetails paymentDetails = paymentDetailsService.addPaymentDetails(paymentDetailsRequest.getAccountNumber(),
                paymentDetailsRequest.getAccountHolderName(), paymentDetailsRequest.getIfsc(),
                paymentDetailsRequest.getBankName(), user);
        return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaymentDetails> getUsersPaymentDetails(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentDetails paymentDetails = paymentDetailsService.getUsersPaymentDetails(user);
        return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
    }
}
