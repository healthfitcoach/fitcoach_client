package com.fitcoach.client.domain.purchase.repository;

import com.fitcoach.client.model.product.PTProduct;
import com.fitcoach.client.model.product.Product.ProductStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PTProductRepository extends JpaRepository<PTProduct, Long> {

  List<PTProduct> findByStatus(ProductStatus status);
}
