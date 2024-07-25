package com.ad.trading.services;

import com.ad.trading.modals.User;
import com.ad.trading.modals.Withdrawal;

import java.util.List;

public interface WithdrawalService {
    Withdrawal requestWithdrawal(Long amount, User user);
    Withdrawal proceedWithdrawal(Long withdrawalId, boolean accept) throws Exception;
    List<Withdrawal> getUsersWithdrawalHistory(User user);
    List<Withdrawal> getAllWithdrawalRequests();
}
