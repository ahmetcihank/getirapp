package org.getir.dao.fragment;

import org.getir.domain.order.ProductOrder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class OrderFragmentDaoImpl implements OrderFragmentDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ProductOrder> getOrdersByTimeInterval(Date startDate, Date endDate) {
      String hql = "from ProductOrder po where po.created>=:startDate and po.created<=:endDate";

      List<ProductOrder> productOrders = ( List<ProductOrder>) entityManager
                                     .createQuery(hql)
                                     .setParameter("startDate", startDate)
                                     .setParameter("endDate", endDate)
                                     .getResultList();

        return productOrders;
    }

    @Override
    public List<ProductOrder> getOrderByMonth(int month, long userId) {
        String hql = "from ProductOrder po where month(po.created)=:date and customer_id=:userId";

        List<ProductOrder> productOrders = ( List<ProductOrder>) entityManager
                                            .createQuery(hql)
                                            .setParameter("date", month)
                                            .setParameter("userId", userId)
                                            .getResultList();

        return productOrders;
    }


}

