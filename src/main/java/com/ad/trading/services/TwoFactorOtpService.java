package com.ad.trading.services;

import com.ad.trading.modals.TwoFactorOTP;
import com.ad.trading.modals.User;
import org.springframework.stereotype.Service;

@Service
public interface TwoFactorOtpService {
    TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);
    TwoFactorOTP findByUser(Long userId);
    TwoFactorOTP findById(String id);
    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp);
    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp);
}
