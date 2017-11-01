package com.supply.store.module.order.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supply.store.config.contants.OrderStatus;
import com.supply.store.entity.po.OrderDetailPo;
import com.supply.store.entity.po.OrderPo;
import com.supply.store.exception.SupplyException;
import com.supply.store.module.order.repository.OrderRepository;
import com.supply.store.module.order.service.OrderService;
import com.supply.store.module.product.repository.ProductRepository;

@Service
public class OrderServiceImpl implements OrderService
{
	private OrderRepository mOrderRepository;
	private ProductRepository mProductRepository;
	
	
	@Transactional
	@Override
	public void createOrder(OrderPo order, List<OrderDetailPo> orderDetails)
	{
		//创建订单
		int size = orderDetails.size();
		order.setProductNum(size);
		order.setTotalPrice(calcTotalPrice(orderDetails));
		order.setOrderStatus(OrderStatus.STATUS_UNDER);
		int row = mOrderRepository.saveOrder(order);
		if (row != 1)
		{
			throw new SupplyException("创建订单失败,请稍后重试");
		}
		
		int[] rows = mOrderRepository.saveOrderDetails(orderDetails, order.getId());
		if (rows == null || rows.length != size)
		{
			throw new SupplyException("创建订单失败,请稍后重试");
		}
		//修改库存
		for (OrderDetailPo detail : orderDetails)
		{
			mProductRepository.reduceNum(detail.getProductNum(), detail.getProductId());
		}
	
	}
	
	private BigDecimal calcTotalPrice(List<OrderDetailPo> orderDetails)
	{
		BigDecimal total = new BigDecimal("0.00");
		for (OrderDetailPo detail : orderDetails)
		{
			total = total.add(detail.getUnitPrice().multiply(new BigDecimal(detail.getProductNum())));
		}
		
		return total;
	}
	
	@Resource(name="orderRepository")
	public void setProductRepository(OrderRepository orderRepository)
	{
		this.mOrderRepository = orderRepository;
	}


	@Resource(name="productRepository")
	public void setProductRepository(ProductRepository productRepository)
	{
		this.mProductRepository = productRepository;
	}



	


	
	
}
