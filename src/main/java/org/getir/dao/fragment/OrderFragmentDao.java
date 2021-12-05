package org.getir.dao.fragment;

import org.getir.domain.order.ProductOrder;

import java.util.Date;
import java.util.List;

public interface OrderFragmentDao {
    public List<ProductOrder>getOrdersByTimeInterval(Date startDate, Date endDate);
    public List<ProductOrder> getOrderByMonth(int month, long userId);
}
