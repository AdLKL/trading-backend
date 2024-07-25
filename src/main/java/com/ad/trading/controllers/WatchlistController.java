package com.ad.trading.controllers;

import com.ad.trading.modals.Coin;
import com.ad.trading.modals.User;
import com.ad.trading.modals.Watchlist;
import com.ad.trading.services.CoinService;
import com.ad.trading.services.UserService;
import com.ad.trading.services.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
    private final WatchlistService watchlistService;
    private final UserService userService;
    private final CoinService coinService;

    @Autowired
    public WatchlistController(WatchlistService watchlistService, UserService userService, CoinService coinService) {
        this.watchlistService = watchlistService;
        this.userService = userService;
        this.coinService = coinService;
    }

    @GetMapping("/user")
    public ResponseEntity<Watchlist> getUserWatchlist(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Watchlist watchlist = watchlistService.findUserWatchlist(user.getId());
        return ResponseEntity.ok(watchlist);
    }

    @PostMapping("/create")
    public ResponseEntity<Watchlist> createWatchlist(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Watchlist createdWatchlist = watchlistService.createWatchlist(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWatchlist);
    }

    @GetMapping("{watchlistId}")
    public ResponseEntity<Watchlist> getWatchlistById(@PathVariable("watchlistId") Long watchlistId) throws Exception {
        Watchlist watchlist = watchlistService.findById(watchlistId);
        return ResponseEntity.ok(watchlist);
    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchlist(@RequestHeader("Authorization") String jwt,
                                                   @PathVariable String coinId) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(coinId);
        Coin addedCoin = watchlistService.addItemToWatchlist(coin, user);
        return ResponseEntity.ok(addedCoin);
    }

}
