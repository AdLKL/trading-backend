package com.ad.trading.repositories;

import com.ad.trading.modals.Wallet;
import com.ad.trading.modals.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<WalletTransaction, Integer> {
    List<WalletTransaction> findByWallet(Wallet wallet);
}
