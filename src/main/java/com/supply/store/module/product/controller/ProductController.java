package com.supply.store.module.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supply.store.base.controller.BaseController;
import com.supply.store.beanutil.WrappedBeanCopier;
import com.supply.store.entity.PageInfo;
import com.supply.store.entity.base.BaseResponse;
import com.supply.store.entity.dto.ProductDto;
import com.supply.store.entity.po.ProductPo;
import com.supply.store.module.product.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "产品相关")
@RestController
@RequestMapping("/front/product")
public class ProductController extends BaseController
{

	private ProductService mProductService;

	@Autowired
	public ProductController(ProductService productService)
	{
		this.mProductService = productService;
	}

	
	
	@ApiOperation(httpMethod = "GET", value = "获取所有产品", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(method = RequestMethod.GET, value="/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<List<ProductDto>> findAllProducts( @RequestParam("page") long page, @RequestParam("num") int num)
	{
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(page);
		pageInfo.setItemNum(num);

		List<ProductPo> products = mProductService.findProducts(pageInfo);
		return getResponse(WrappedBeanCopier.copyPropertiesOfList(products, ProductDto.class));
	}
	

}
