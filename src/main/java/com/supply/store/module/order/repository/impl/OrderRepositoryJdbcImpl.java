package com.supply.store.module.order.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.supply.store.entity.po.OrderDetailPo;
import com.supply.store.entity.po.OrderPo;
import com.supply.store.module.order.repository.OrderRepository;
import com.supply.store.util.TimeUtil;

@Repository(value="orderRepository")
public class OrderRepositoryJdbcImpl implements OrderRepository
{


	private static final String SQL_SAVE_ORDER = "INSERT INTO t_order (store_id, total_price, product_num, receiving_address, contacts, order_status, order_remark, create_time) VALUES (:store_id, :total_price, :product_num, :receiving_address, :contacts, :order_status, :order_remark, :create_time)";
	
	private static final String SQL_SAVE_ORDER_DETAIL = "INSERT INTO t_order_detail(order_id, product_id, product_name, product_num, unit_price, product_unit, create_time) VALUES (:order_id, :product_id, :product_name, :product_num, :unit_price, :product_unit, :create_time)";
	
	

	private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

	
	@Autowired
	public OrderRepositoryJdbcImpl(
			@Qualifier(value = "namedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.mNamedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}


	@Override
	public int saveOrder(OrderPo order)
	{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("store_id", order.getStoreId());
		paramSource.addValue("total_price", order.getTotalPrice());
		paramSource.addValue("product_num", order.getProductNum());
		paramSource.addValue("receiving_address", order.getReceivingAddress());
		
		paramSource.addValue("contacts", order.getContacts());
		paramSource.addValue("order_status", order.getOrderStatus().ordinal());
		paramSource.addValue("order_remark", order.getOrderRemark());
		
		paramSource.addValue("create_time", TimeUtil.getCurrentTimestamp());
		int effectedRows = this.mNamedParameterJdbcTemplate.update(SQL_SAVE_ORDER, paramSource, keyHolder,
				new String[] { "id" });
		order.setId(keyHolder.getKey().longValue());

		return effectedRows;
	}


	@Override
	public int[] saveOrderDetails(List<OrderDetailPo> orderDetails, long orderId)
	{
		int len = orderDetails.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		
		for (int i=0; i<len; i++)
		{
			Map<String, Object> batchValue = new HashMap<String, Object>();
			OrderDetailPo detail = orderDetails.get(i);
			
			batchValue.put("order_id", orderId);
			batchValue.put("product_id", detail.getProductId());
			
			batchValue.put("product_name", detail.getProductName());
			batchValue.put("product_num", detail.getProductNum());
			batchValue.put("unit_price", detail.getUnitPrice());
			batchValue.put("product_unit", detail.getProductUnit());
			batchValue.put("create_time", TimeUtil.getCurrentTimestamp());
			
			batchValues[i] = batchValue;
		}
		
		return this.mNamedParameterJdbcTemplate.batchUpdate(SQL_SAVE_ORDER_DETAIL, batchValues);
	}



}
