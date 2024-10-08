package com.ad.trading.modals;

import com.ad.trading.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String otp;
    @OneToOne
    private User user;
    private String mobile;
    private String email;
    private VerificationType verificationType;
}
