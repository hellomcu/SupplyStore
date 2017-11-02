package com.supply.store.module.product.repository;

import java.util.List;

import com.supply.store.base.repository.Repository;
import com.supply.store.entity.PageInfo;
import com.supply.store.entity.po.ProductPo;

public interface ProductRepository extends Repository
{
	int reduceNum(int num, long id);
	
	List<ProductPo> findAll(PageInfo pageInfo);
}
