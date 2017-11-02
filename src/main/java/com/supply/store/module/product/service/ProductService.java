package com.supply.store.module.product.service;

import java.util.List;

import com.supply.store.base.service.BaseService;
import com.supply.store.entity.PageInfo;
import com.supply.store.entity.po.ProductPo;

public interface ProductService extends BaseService
{

	List<ProductPo> findProducts(PageInfo page);
	

}
