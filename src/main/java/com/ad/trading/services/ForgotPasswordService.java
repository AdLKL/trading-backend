package com.ad.trading.services;

import com.ad.trading.domain.VerificationType;
import com.ad.trading.modals.ForgotPasswordToken;
import com.ad.trading.modals.User;

public interface ForgotPasswordService {
    ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo);
    ForgotPasswordToken findById(String id);
    ForgotPasswordToken findByUser(Long userId);
    void deleteToken(ForgotPasswordToken token);
}
