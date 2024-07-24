package com.ad.trading.services;

import com.ad.trading.domain.VerificationType;
import com.ad.trading.modals.User;
import com.ad.trading.modals.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);
    VerificationCode getVerificationCodeById(Long id) throws Exception;
    VerificationCode getVerificationCodeByUser(Long userId);
    void deleteVerificationCodeById(VerificationCode verificationCode);
}
