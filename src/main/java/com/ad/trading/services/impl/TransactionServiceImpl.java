package com.ad.trading.services.impl;

import com.ad.trading.domain.WalletTransactionType;
import com.ad.trading.modals.Wallet;
import com.ad.trading.modals.WalletTransaction;
import com.ad.trading.repositories.TransactionRepository;
import com.ad.trading.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<WalletTransaction> getTransactionByWallet(Wallet wallet) {
        return transactionRepository.findByWallet(wallet);
    }

    @Override
    public WalletTransaction createTransaction(Wallet userWallet, WalletTransactionType walletTransactionType, String transferId, String purpose, Long amount) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(userWallet);
        transaction.setType(walletTransactionType);
        transaction.setDate(LocalDate.now());
        transaction.setTransferId(transferId);
        transaction.setPurpose(purpose);
        transaction.setAmount(amount);

        return transactionRepository.save(transaction);
    }
}
