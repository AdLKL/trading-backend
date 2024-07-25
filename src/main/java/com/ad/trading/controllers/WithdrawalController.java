package com.ad.trading.controllers;

import com.ad.trading.modals.User;
import com.ad.trading.modals.Wallet;
import com.ad.trading.modals.Withdrawal;
import com.ad.trading.services.UserService;
import com.ad.trading.services.WalletService;
import com.ad.trading.services.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WithdrawalController {
    private final WithdrawalService withdrawalService;
    private final WalletService walletService;
    private final UserService userService;
    //private final WalletTransactionService walletTransactionService;

    @Autowired
    public WithdrawalController(WithdrawalService withdrawalService, WalletService walletService, UserService userService) {
        this.withdrawalService = withdrawalService;
        this.walletService = walletService;
        this.userService = userService;
        //this.walletTransactionService = walletTransactionService;
    }

    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<?> withdrawalRequest(@PathVariable Long amount,
                                               @RequestHeader("Authorization0") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);

        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
        walletService.addBalance(userWallet, -withdrawal.getAmount());

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(@PathVariable Long id,
                                               @PathVariable boolean accept,
                                               @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Withdrawal withdrawal = withdrawalService.proceedWithdrawal(id, accept);
        Wallet userWallet = walletService.getUserWallet(user);
        if(!accept) {
            walletService.addBalance(userWallet, withdrawal.getAmount());
        }
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/api/withdrawal")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Withdrawal> withdrawal = withdrawalService.getUsersWithdrawalHistory(user);
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequests(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Withdrawal> withdrawal = withdrawalService.getAllWithdrawalRequests();
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

}
