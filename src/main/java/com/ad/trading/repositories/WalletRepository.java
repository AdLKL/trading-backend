package com.ad.trading.repositories;

import com.ad.trading.modals.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByUserId(Long userId);
}
