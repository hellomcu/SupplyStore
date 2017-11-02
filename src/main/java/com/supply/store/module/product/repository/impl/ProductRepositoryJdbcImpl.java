package com.supply.store.module.product.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.supply.store.module.product.repository.ProductRepository;


@Repository(value="productRepository")
public class ProductRepositoryJdbcImpl implements ProductRepository
{

	private static final String SQL_REDUCE_NUM = "UPDATE t_product SET product_num=product_num-:product_num WHERE id=:id AND status=0";
	
	//private static final String SQL_UPDATE = "UPDATE t_store SET store_name=:store_name, store_place=:store_place, contacts=:contacts, description=:description WHERE status=0 AND id=:id";
	
	private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

	
	@Autowired
	public ProductRepositoryJdbcImpl(
			@Qualifier(value = "namedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.mNamedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	

	@Override
	public int reduceNum(int num, long id)
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", id);
		paramSource.addValue("product_num", num);
		int effectedRows = this.mNamedParameterJdbcTemplate.update(SQL_REDUCE_NUM, paramSource);
		return effectedRows;
	}


}
