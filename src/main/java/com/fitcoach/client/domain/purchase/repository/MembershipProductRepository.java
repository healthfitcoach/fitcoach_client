package com.fitcoach.client.domain.purchase.repository;

import com.fitcoach.client.model.product.MembershipProduct;
import com.fitcoach.client.model.product.Product.ProductStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipProductRepository extends JpaRepository<MembershipProduct, Long> {

  List<MembershipProduct> findByStatus(ProductStatus status);
}
