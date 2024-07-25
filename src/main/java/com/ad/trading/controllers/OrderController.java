package com.ad.trading.controllers;

import com.ad.trading.domain.OrderType;
import com.ad.trading.modals.Coin;
import com.ad.trading.modals.Order;
import com.ad.trading.modals.User;
import com.ad.trading.requests.CreateOrderRequest;
import com.ad.trading.services.CoinService;
import com.ad.trading.services.OrderService;
import com.ad.trading.services.UserService;
import com.ad.trading.services.WalletTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final CoinService coinService;
    private final WalletTransactionService walletTransactionService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, CoinService coinService, WalletTransactionService walletTransactionService) {
        this.orderService = orderService;
        this.userService = userService;
        this.coinService = coinService;
        this.walletTransactionService = walletTransactionService;
    }

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(@RequestHeader("Authorization") String jwt, @RequestBody CreateOrderRequest request) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(request.getCoinId());
        Order order = orderService.processOrder(coin, request.getQuantity(), request.getOrderType(), user);

        return ResponseEntity.ok(order);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<Order> getOrderById(@RequestHeader("Authorization") String jwt, @PathVariable Long orderId) throws Exception {
        if(jwt == null || !jwt.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.getOrderById(orderId);
        if(order.getUser().getId().equals(user.getId())) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrdersForUser(@RequestHeader("Authorization") String jwt,
                                                           @RequestParam(required = false) OrderType orderType,
                                                           @RequestParam(required = false) String assetSymbol) throws Exception {
        if(jwt == null || !jwt.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }
        Long userId = userService.findUserProfileByJwt(jwt).getId();
        List<Order> userOrders = orderService.getAllOrdersOfUser(userId, orderType, assetSymbol);

        return ResponseEntity.ok(userOrders);
    }
}
