package com.ad.trading.services.impl;

import com.ad.trading.modals.Coin;
import com.ad.trading.modals.User;
import com.ad.trading.modals.Watchlist;
import com.ad.trading.repositories.WatchlistRepository;
import com.ad.trading.services.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchlistServiceImpl implements WatchlistService {
    private final WatchlistRepository watchlistRepository;

    @Autowired
    public WatchlistServiceImpl(WatchlistRepository watchlistRepository) {
        this.watchlistRepository = watchlistRepository;
    }

    @Override
    public Watchlist findUserWatchlist(Long userId) throws Exception {
        Watchlist watchlist = watchlistRepository.findByUserId(userId);
        if (watchlist == null) {
            throw new Exception("Watchlist not found");
        }
        return watchlist;
    }

    @Override
    public Watchlist createWatchlist(User user) {
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        return watchlistRepository.save(watchlist);
    }

    @Override
    public Watchlist findById(Long id) throws Exception {
        Optional<Watchlist> watchlist = watchlistRepository.findById(id);
        if(watchlist.isEmpty()) {
            throw new Exception("Watchlist not found");
        }
        return watchlist.get();
    }

    @Override
    public Coin addItemToWatchlist(Coin coin, User user) throws Exception {
        Watchlist watchlist = findUserWatchlist(user.getId());
        if(watchlist.getCoins().contains(coin)) {
            watchlist.getCoins().remove(coin);
        } else {
            watchlist.getCoins().add(coin);
        }
        watchlistRepository.save(watchlist);
        return coin;

    }
}
