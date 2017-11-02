package com.supply.store.module.product.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.supply.store.entity.PageInfo;
import com.supply.store.entity.po.ProductPo;
import com.supply.store.module.product.repository.ProductRepository;


@Repository(value="productRepository")
public class ProductRepositoryJdbcImpl implements ProductRepository
{

	private static final String SQL_REDUCE_NUM = "UPDATE t_product SET product_num=product_num-:product_num WHERE id=:id AND status=0";
	
	
	private static final String SQL_QUERY = "SELECT p.id product_id, p.product_name product_name, p.total_num total_num," + 
			"p.product_num product_num, p.product_price product_price, p.product_unit, p.product_place product_place, p.description description, p.create_time create_time," + 
			"c.id category_id,c.category_name category_name," + 
			"c2.id parent_id, c2.category_name parent_name" + 
			" FROM t_product p LEFT JOIN t_category c ON p.category_id = c.id" + 
			" LEFT JOIN t_category c2 ON c2.id = c.parent_id" + 
			" WHERE p.status = 0 AND c.status = 0  AND c2.status = 0" + 
			" ORDER BY p.create_time DESC LIMIT :start, :num";
	
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

	@Override
	public List<ProductPo> findAll(PageInfo pageInfo)
	{
		List<ProductPo> products = new ArrayList<ProductPo>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("start", pageInfo.getStartItemNum());
		paramSource.addValue("num", pageInfo.getItemNum());
		SqlRowSet rowSet = this.mNamedParameterJdbcTemplate.queryForRowSet(SQL_QUERY, paramSource);
		while (rowSet.next())
		{
			ProductPo product = new ProductPo();
			product.setId(rowSet.getLong("product_id"));
			product.setProductName(rowSet.getString("product_name"));
			product.setTotalNum(rowSet.getInt("total_num"));
			product.setProductNum(rowSet.getInt("product_num"));
			product.setProductPrice(rowSet.getBigDecimal("product_price"));
			product.setProductUnit(rowSet.getString("product_unit"));
			product.setProductPlace(rowSet.getString("product_place"));
			product.setDescription(rowSet.getString("description"));
			product.setCreateTime(rowSet.getTimestamp("create_time"));
			product.setCategoryId(rowSet.getLong("category_id"));
			product.setCategoryName(rowSet.getString("category_name"));
			product.setParentId(rowSet.getLong("parent_id"));
			product.setParentName(rowSet.getString("parent_name"));
	
			products.add(product);
		}
		return products;
	}
}
