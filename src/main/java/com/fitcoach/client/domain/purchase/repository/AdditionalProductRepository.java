package com.fitcoach.client.domain.purchase.repository;

import com.fitcoach.client.model.product.AdditionalProduct;
import com.fitcoach.client.model.product.Product.ProductStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalProductRepository extends JpaRepository<AdditionalProduct, Long> {

  List<AdditionalProduct> findByStatus(ProductStatus status);
}
