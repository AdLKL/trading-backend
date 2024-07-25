package com.ad.trading.modals;

import com.ad.trading.domain.OrderStatus;
import com.ad.trading.domain.OrderType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    @Column(nullable = false)
    private OrderType orderType;
    @Column(nullable = false)
    private BigDecimal price;
    private LocalDateTime timestamp = LocalDateTime.now();
    @Column(nullable = false)
    private OrderStatus status;
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderItem orderItem;
}
