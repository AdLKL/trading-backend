package com.ad.trading.repositories;

import com.ad.trading.modals.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    VerificationCode findByUserId(Long id);
}
