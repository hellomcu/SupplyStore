package com.supply.store.module.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supply.store.base.controller.BaseController;
import com.supply.store.beanutil.WrappedBeanCopier;
import com.supply.store.entity.PageInfo;
import com.supply.store.entity.base.BaseResponse;
import com.supply.store.entity.dto.CreateOrderDto;
import com.supply.store.entity.dto.OrderDetailDto;
import com.supply.store.entity.dto.OrderDto;
import com.supply.store.entity.po.OrderDetailPo;
import com.supply.store.entity.po.OrderPo;
import com.supply.store.module.order.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "订单相关")
@RestController
@RequestMapping("/front/order")
public class OrderController extends BaseController
{

	private OrderService mOrderService;

	@Autowired
	public OrderController(OrderService orderService)
	{
		this.mOrderService = orderService;
	}

	

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "POST", value = "创建订单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> createOrder(@RequestBody CreateOrderDto createOrderDto)
	{
		OrderPo order = WrappedBeanCopier.copyProperties(createOrderDto, OrderPo.class);
		List<OrderDetailPo> details = WrappedBeanCopier.copyPropertiesOfList(createOrderDto.getOrderDetails(), OrderDetailPo.class);
		mOrderService.createOrder(order, details);
		return getResponse();
	}
	
	@ApiOperation(httpMethod = "GET", value = "获取我的订单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(method = RequestMethod.GET, value="/my_orders", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<List<OrderDto>> findMyOrders(@RequestParam("store_id") long storeId, @RequestParam("page") long page, @RequestParam("num") int num)
	{
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(page);
		pageInfo.setItemNum(num);

		List<OrderDto> list = new ArrayList<OrderDto>();
		Map<OrderPo, List<OrderDetailPo>> map = mOrderService.findMyOrders(pageInfo, storeId);
		for (OrderPo key: map.keySet())
		{
			OrderDto order = WrappedBeanCopier.copyProperties(key, OrderDto.class);
			order.setDetails(WrappedBeanCopier.copyPropertiesOfList(map.get(key), OrderDetailDto.class));
			list.add(order);
		}
		
		return getResponse(list);
	}
	
}
