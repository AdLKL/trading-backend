package com.ad.trading.repositories;

import com.ad.trading.modals.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {
    TwoFactorOTP findByUserId(Long userId);
}
