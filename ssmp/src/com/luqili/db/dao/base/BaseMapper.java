package com.luqili.db.dao.base;

import java.io.Serializable;

public interface BaseMapper<T> {
	public abstract int deleteByPrimaryKey(Integer id);
	public abstract int insert(T record);
	public abstract int insertSelective(T record);
	public abstract T selectByPrimaryKey(Serializable id);
	public abstract int updateByPrimaryKeySelective(T record);
	public abstract int updateByPrimaryKey(T record);
}
