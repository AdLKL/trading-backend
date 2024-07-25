package com.ad.trading.services;

import com.ad.trading.modals.PaymentDetails;
import com.ad.trading.modals.User;

public interface PaymentDetailsService {
    PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName,
                                            String ifsc, String bankName, User user);
    PaymentDetails getUsersPaymentDetails(User user);
}
