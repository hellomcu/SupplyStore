package com.supply.store.module.order.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.supply.store.config.contants.OrderStatus;
import com.supply.store.entity.PageInfo;
import com.supply.store.entity.po.OrderDetailPo;
import com.supply.store.entity.po.OrderPo;
import com.supply.store.module.order.repository.OrderRepository;
import com.supply.store.util.TimeUtil;

@Repository(value="orderRepository")
public class OrderRepositoryJdbcImpl implements OrderRepository
{


	private static final String SQL_SAVE_ORDER = "INSERT INTO t_order (store_id, total_price, product_num, total_num, receiving_address, contacts, order_status, order_remark, create_time) VALUES (:store_id, :total_price, :product_num, :total_num, :receiving_address, :contacts, :order_status, :order_remark, :create_time)";
	
	
	private static final String SQL_SAVE_ORDER_DETAIL = "INSERT INTO t_order_detail(order_id, product_id, product_name, product_num, unit_price, product_unit, create_time) VALUES (:order_id, :product_id, :product_name, :product_num, :unit_price, :product_unit, :create_time)";
	
	
	private static final String SQL_QUERY_BY_STOREID = "SELECT o.id order_id, o.total_price total_price, o.order_status order_status, o.create_time create_time," + 
			" od.id detail_id, od.product_id product_id, od.product_name product_name, od.product_num product_num, od.unit_price unit_price, od.product_unit product_unit" + 
			" FROM t_order o" + 
			" LEFT JOIN t_order_detail od" + 
			" ON o.id = od.order_id AND od.status = 0" + 
			" WHERE o.status = 0" + 
			" AND o.id IN(SELECT t.id FROM (SELECT id FROM t_order WHERE t_order.store_id = :store_id AND t_order.status = 0 LIMIT :start, :num)AS t)" + 
			" ORDER BY o.create_time DESC";
	
	
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
		paramSource.addValue("total_num", order.getTotalNum());
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


	@Override
	public Map<OrderPo, List<OrderDetailPo>>  findByStoreId(PageInfo pageInfo, long storeId)
	{
		Map<OrderPo, List<OrderDetailPo>> map = new HashMap<OrderPo, List<OrderDetailPo>>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("store_id", storeId);
		paramSource.addValue("start", pageInfo.getStartItemNum());
		paramSource.addValue("num", pageInfo.getItemNum());
		SqlRowSet rowSet = this.mNamedParameterJdbcTemplate.queryForRowSet(SQL_QUERY_BY_STOREID, paramSource);
		
		while (rowSet.next())
		{
			OrderPo order = new OrderPo();
			order.setId(rowSet.getLong("order_id"));
			if (!map.containsKey(order))
			{
				order.setTotalPrice(rowSet.getBigDecimal("total_price"));
				order.setOrderStatus(OrderStatus.values()[rowSet.getInt("order_status")]);
				order.setCreateTime(rowSet.getTimestamp("create_time"));
				map.put(order, null);
			}
			
			List<OrderDetailPo> list = map.get(order);
			if (list == null)
			{
				list = new ArrayList<OrderDetailPo>();
				map.put(order, list);
			}
			OrderDetailPo detail = new OrderDetailPo();
			detail.setId(rowSet.getLong("detail_id"));
			detail.setProductId(rowSet.getLong("product_id"));
			detail.setProductName(rowSet.getString("product_name"));
			detail.setProductNum(rowSet.getInt("product_num"));
			detail.setUnitPrice(rowSet.getBigDecimal("unit_price"));
			detail.setProductUnit(rowSet.getString("product_unit"));
			list.add(detail);
		}
		return map;
	}
}
