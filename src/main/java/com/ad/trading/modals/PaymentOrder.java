package com.ad.trading.modals;

import com.ad.trading.domain.PaymentMethod;
import com.ad.trading.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long amount;
    private PaymentOrderStatus status;
    private PaymentMethod paymentMethod;
    @ManyToOne
    private User user;
}
