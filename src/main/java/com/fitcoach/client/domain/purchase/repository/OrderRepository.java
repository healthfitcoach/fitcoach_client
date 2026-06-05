package com.fitcoach.client.domain.purchase.repository;

import com.fitcoach.client.model.order.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

  List<Order> findByMemberIdOrderByOrderDateTimeDesc(String memberId);
}
