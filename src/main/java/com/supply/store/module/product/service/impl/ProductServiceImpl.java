package com.supply.store.module.product.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.supply.store.entity.PageInfo;
import com.supply.store.entity.po.ProductPo;
import com.supply.store.module.product.repository.ProductRepository;
import com.supply.store.module.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService
{
	private ProductRepository mProductRepository;
	

	
	@Override
	public List<ProductPo> findProducts(PageInfo page)
	{
		return mProductRepository.findAll(page);
	}
	
	
	@Resource(name="productRepository")
	public void setProductRepository(ProductRepository productRepository)
	{
		this.mProductRepository = productRepository;
	}

	


	
	
}
