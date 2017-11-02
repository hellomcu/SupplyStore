package com.supply.store.module.order.service;

import java.util.List;
import java.util.Map;

import com.supply.store.base.service.BaseService;
import com.supply.store.entity.PageInfo;
import com.supply.store.entity.po.OrderDetailPo;
import com.supply.store.entity.po.OrderPo;

public interface OrderService extends BaseService
{

	void createOrder(OrderPo order, List<OrderDetailPo> orderDetails);

	
	Map<OrderPo, List<OrderDetailPo>> findMyOrders(PageInfo page, long storeId);
}
