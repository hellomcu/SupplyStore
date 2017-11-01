package com.supply.store.module.product.repository;

import com.supply.store.base.repository.Repository;

public interface ProductRepository extends Repository
{
	int reduceNum(int num, long id);
}
