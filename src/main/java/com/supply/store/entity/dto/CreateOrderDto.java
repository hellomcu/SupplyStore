package com.supply.store.entity.dto;

import java.math.BigDecimal;
import java.util.List;

import com.supply.store.entity.base.BaseDto;

public class CreateOrderDto extends BaseDto
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1602047974450700054L;
	
	
	private long storeId;
	private String receivingAddress;
	private String contacts;
	private String orderRemark;
	private List<CreateOrderDetailDto> orderDetails;
	
	public long getStoreId()
	{
		return storeId;
	}
	public void setStoreId(long storeId)
	{
		this.storeId = storeId;
	}
	public String getReceivingAddress()
	{
		return receivingAddress;
	}
	public void setReceivingAddress(String receivingAddress)
	{
		this.receivingAddress = receivingAddress;
	}
	public String getContacts()
	{
		return contacts;
	}
	public void setContacts(String contacts)
	{
		this.contacts = contacts;
	}
	public String getOrderRemark()
	{
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark)
	{
		this.orderRemark = orderRemark;
	}
	public List<CreateOrderDetailDto> getOrderDetails()
	{
		return orderDetails;
	}
	public void setOrderDetails(List<CreateOrderDetailDto> orderDetails)
	{
		this.orderDetails = orderDetails;
	}
	
	

}
