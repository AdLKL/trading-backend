package com.ad.trading.controllers;

import com.ad.trading.modals.*;
import com.ad.trading.services.OrderService;
import com.ad.trading.services.PaymentService;
import com.ad.trading.services.UserService;
import com.ad.trading.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletService walletService;
    private final UserService userService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    @Autowired
    public WalletController(WalletService walletService, UserService userService, OrderService orderService, PaymentService paymentService) {
        this.walletService = walletService;
        this.userService = userService;
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt,
                                                         @PathVariable Long walletId,
                                                         @RequestBody WalletTransaction request) throws Exception {
        User senderUser = userService.findUserProfileByJwt(jwt);
        Wallet receiverWallet = walletService.findWalletById(walletId);
        Wallet wallet = walletService.walletToWalletTransfer(senderUser, receiverWallet, request.getAmount());
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt,
                                                  @PathVariable Long orderId) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.getOrderById(orderId);

        Wallet wallet = walletService.payOrderPayment(order, user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/deposit")
    public ResponseEntity<Wallet> addBalanceToWallet(@RequestHeader("Authorization") String jwt,
                                                     @RequestParam(name = "order_id") Long orderId,
                                                     @RequestParam(name = "payment_id") String paymentId) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        PaymentOrder order = paymentService.getPaymentOrderById(orderId);

        Boolean status = paymentService.proceedPaymentOrder(order, paymentId);

        if(status) {
            wallet = walletService.addBalance(wallet, order.getAmount());
        }

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
}
