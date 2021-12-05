package org.getir.dao;

import org.getir.dao.fragment.OrderFragmentDao;
import org.getir.domain.order.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<ProductOrder, Long>, OrderFragmentDao {
   public ProductOrder findByOrderNumber(String orderNumber);
}
