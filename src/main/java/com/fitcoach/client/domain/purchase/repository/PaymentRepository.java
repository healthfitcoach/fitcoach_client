package com.fitcoach.client.domain.purchase.repository;

import com.fitcoach.client.model.order.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
