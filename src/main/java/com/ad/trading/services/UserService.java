package com.ad.trading.services;

import com.ad.trading.domain.VerificationType;
import com.ad.trading.modals.User;

public interface UserService {
    User findUserProfileByJwt(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
    User findUserById(Long id) throws Exception;

    User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user);

    void updatePassword(User user, String newPassword);

}
