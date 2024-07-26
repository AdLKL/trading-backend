package com.ad.trading.services;

import com.ad.trading.domain.WalletTransactionType;
import com.ad.trading.modals.Wallet;
import com.ad.trading.modals.WalletTransaction;

import java.util.List;

public interface TransactionService {
    List<WalletTransaction> getTransactionByWallet(Wallet wallet);

    WalletTransaction createTransaction(Wallet userWallet, WalletTransactionType walletTransactionType, String transferId, String purpose, Long amount);
}
