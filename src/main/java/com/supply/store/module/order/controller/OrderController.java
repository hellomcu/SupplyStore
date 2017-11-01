package com.supply.store.module.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.supply.store.base.controller.BaseController;
import com.supply.store.beanutil.WrappedBeanCopier;
import com.supply.store.entity.base.BaseResponse;
import com.supply.store.entity.dto.CreateOrderDto;
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

	
//	@ApiOperation(httpMethod = "GET", value = "获取所有订单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@RequestMapping(method = RequestMethod.GET, value="/orders", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public BaseResponse<List<OrderDto>> findAllStores( @RequestParam("page") long page, @RequestParam("num") int num)
//	{
//		PageInfo pageInfo = new PageInfo();
//		pageInfo.setCurrentPage(page);
//		pageInfo.setItemNum(num);
//
//		List<OrderPo> products = mOrderService.findOrders(pageInfo);
//		return getResponse(WrappedBeanCopier.copyPropertiesOfList(products, OrderDto.class));
//	}
//	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "POST", value = "创建订单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> createOrder(@RequestBody CreateOrderDto createOrderDto)
	{
		OrderPo order = WrappedBeanCopier.copyProperties(createOrderDto, OrderPo.class);
		List<OrderDetailPo> details = WrappedBeanCopier.copyPropertiesOfList(createOrderDto.getOrderDetails(), OrderDetailPo.class);
		mOrderService.createOrder(order, details);
		return getResponse();
	}
}
