package com.ad.trading.repositories;

import com.ad.trading.modals.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    Watchlist findByUserId(Long userId);
}
