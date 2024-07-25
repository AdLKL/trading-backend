package com.ad.trading.services;

import com.ad.trading.modals.Coin;
import com.ad.trading.modals.User;
import com.ad.trading.modals.Watchlist;

public interface WatchlistService {
    Watchlist findUserWatchlist(Long userId) throws Exception;
    Watchlist createWatchlist(User user);
    Watchlist findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin, User user) throws Exception;
}
