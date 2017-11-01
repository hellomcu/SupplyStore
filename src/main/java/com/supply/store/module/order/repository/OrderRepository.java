package com.supply.store.module.order.repository;

import java.util.List;

import com.supply.store.base.repository.Repository;
import com.supply.store.entity.po.OrderDetailPo;
import com.supply.store.entity.po.OrderPo;

public interface OrderRepository extends Repository
{
	
	int saveOrder(OrderPo order);
	
	int[] saveOrderDetails(List<OrderDetailPo> orderDetails, long orderId);
	
}
