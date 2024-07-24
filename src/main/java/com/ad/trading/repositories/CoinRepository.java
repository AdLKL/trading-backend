package com.ad.trading.repositories;

import com.ad.trading.modals.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, String> {
}
