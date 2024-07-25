package com.ad.trading.services;

import com.ad.trading.modals.Order;
import com.ad.trading.modals.User;
import com.ad.trading.modals.Wallet;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {
    Wallet getUserWallet (User user);
    Wallet addBalance(Wallet wallet, Long money);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User sender, Wallet receiver, Long amount) throws Exception;
    Wallet payOrderPayment(Order order, User user) throws Exception;

}
