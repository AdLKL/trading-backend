package com.ad.trading.controllers;

import com.ad.trading.modals.User;
import com.ad.trading.modals.Wallet;
import com.ad.trading.modals.WalletTransaction;
import com.ad.trading.services.TransactionService;
import com.ad.trading.services.UserService;
import com.ad.trading.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {
    private final WalletService walletService;
    private final UserService userService;
    private final TransactionService transactionService;

    public TransactionController(WalletService walletService, UserService userService, TransactionService transactionService) {
        this.walletService = walletService;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/api/transactions")
    public ResponseEntity<List<WalletTransaction>> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);
        List<WalletTransaction> transactionList = transactionService.getTransactionByWallet(wallet);
        return new ResponseEntity<>(transactionList, HttpStatus.ACCEPTED);
    }

}
