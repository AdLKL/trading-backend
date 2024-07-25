package com.ad.trading.repositories;

import com.ad.trading.modals.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {
    PaymentDetails findByUserId(Long userId);
}
