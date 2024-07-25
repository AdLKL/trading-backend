package com.ad.trading.services.impl;

import com.ad.trading.domain.WithdrawalStatus;
import com.ad.trading.modals.User;
import com.ad.trading.modals.Withdrawal;
import com.ad.trading.repositories.WithdrawalRepository;
import com.ad.trading.services.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {
    private final WithdrawalRepository withdrawalRepository;

    @Autowired
    public WithdrawalServiceImpl(WithdrawalRepository withdrawalRepository) {
        this.withdrawalRepository = withdrawalRepository;
    }

    @Override
    public Withdrawal requestWithdrawal(Long amount, User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setStatus(WithdrawalStatus.PENDING);
        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public Withdrawal proceedWithdrawal(Long withdrawalId, boolean accept) throws Exception {
        Optional<Withdrawal> withdrawal = withdrawalRepository.findById(withdrawalId);
        if(withdrawal.isEmpty()) {
            throw new Exception("Withdrawal not found");
        }
        Withdrawal withdrawal1 = withdrawal.get();

        withdrawal1.setDate(LocalDateTime.now());

        if(accept) {
            withdrawal1.setStatus(WithdrawalStatus.SUCCESS);
        } else {
            withdrawal1.setStatus(WithdrawalStatus.DECLINED);
        }
        return withdrawalRepository.save(withdrawal1);
    }

    @Override
    public List<Withdrawal> getUsersWithdrawalHistory(User user) {
        return withdrawalRepository.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequests() {
        return withdrawalRepository.findAll();
    }
}
