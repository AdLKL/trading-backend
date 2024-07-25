package com.ad.trading.services.impl;

import com.ad.trading.modals.PaymentDetails;
import com.ad.trading.modals.User;
import com.ad.trading.repositories.PaymentDetailsRepository;
import com.ad.trading.services.PaymentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {
    private final PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    public PaymentDetailsServiceImpl(PaymentDetailsRepository paymentDetailsRepository) {
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc, String bankName, User user) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setIfsc(ifsc);
        paymentDetails.setBankName(bankName);
        paymentDetails.setUser(user);
        return paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUsersPaymentDetails(User user) {
        return paymentDetailsRepository.findByUserId(user.getId());
    }
}
