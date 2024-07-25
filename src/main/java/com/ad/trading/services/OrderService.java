package com.ad.trading.services;

import com.ad.trading.domain.OrderType;
import com.ad.trading.modals.Coin;
import com.ad.trading.modals.Order;
import com.ad.trading.modals.OrderItem;
import com.ad.trading.modals.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem orderItem, OrderType orderType);
    Order getOrderById(Long id) throws Exception;
    List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol);
    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
}
