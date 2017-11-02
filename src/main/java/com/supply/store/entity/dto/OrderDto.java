package com.supply.store.entity.dto;

import java.math.BigDecimal;
import java.util.List;

import com.supply.store.config.contants.OrderStatus;
import com.supply.store.entity.base.BaseDto;

public class OrderDto extends BaseDto
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4647962964978600840L;
	private long id;
	private long storeId;
	private BigDecimal totalPrice;
	private int productNum;
	private int totalNum;
	private OrderStatus orderStatus;
	private List<OrderDetailDto> details;

	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public OrderStatus getOrderStatus()
	{
		return orderStatus;
	}
	public long getStoreId()
	{
		return storeId;
	}
	public void setStoreId(long storeId)
	{
		this.storeId = storeId;
	}
	public BigDecimal getTotalPrice()
	{
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice)
	{
		this.totalPrice = totalPrice;
	}
	public int getProductNum()
	{
		return productNum;
	}
	public void setProductNum(int productNum)
	{
		this.productNum = productNum;
	}
	
	public void setOrderStatus(OrderStatus orderStatus)
	{
		this.orderStatus = orderStatus;
	}

	public int getTotalNum()
	{
		return totalNum;
	}
	public void setTotalNum(int totalNum)
	{
		this.totalNum = totalNum;
	}
	public List<OrderDetailDto> getDetails()
	{
		return details;
	}
	public void setDetails(List<OrderDetailDto> details)
	{
		this.details = details;
	}

}
