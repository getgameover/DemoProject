package com.luqili.db.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.jdbc.SqlRunner;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.luqili.db.dao.base.BaseMapper;
import com.luqili.db.dao.tool.ObjectFilter;
import com.luqili.tool.LuException;

/**
 * DAO 基层
 * @author Administrator
 * 2015-7-3 下午06:40:59
 * @param <T>
 */
public abstract class BaseDAO<T> {
	private Class<T> entityClass;
	public static String NameSpace="com.luqili.db.dao.DbUtilMapper";
	
	@Autowired
	private SqlSessionFactoryBean sessionFactory;
	@Autowired
	private BaseMapper<T> baseMapper;

	@SuppressWarnings({ "unchecked", "rawtypes"})
	public BaseDAO() {
		Type getType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) getType).getActualTypeArguments();
		entityClass = (Class) params[0];
	}

	/**
	 * 根据ID 查询一个类
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public T get(Integer id) {
		if (id == null) {
			return null;
		}
		return baseMapper.selectByPrimaryKey(id);
	}

	/**
	 * 保存一个对象
	 * @param entity
	 * @return
	 */
	@Transactional
	public Integer save(T entity) {
		return baseMapper.insert(entity);
	}

	/**
	 * 保存一列对象
	 * @param lt
	 * @return
	 */
	@Transactional
	public void save(List<T> lt) {
		for (T t : lt) {
			this.save(t);
		}
	}

	/**
	 * 更新一个对象
	 * 
	 * @param entity
	 */
	@Transactional
	public void update(T entity) {
		baseMapper.updateByPrimaryKey(entity);
	}

	/**
	 * 更新一列对象
	 * 
	 * @param lt
	 */
	@Transactional
	public void update(List<T> lt) {
		for (T t : lt) {
			this.update(t);
		}
	}

	/**
	 * 删除一个对象
	 * 
	 * @param entity
	 */
	@Transactional
	public void dele(Integer entity) {
		baseMapper.deleteByPrimaryKey(entity);
	}
	
	/**
	 * 删除一列对象
	 * @param lt
	 */
	public void dele(List<Integer> lt){
		for(Integer t:lt){
			this.dele(t);
		}
	}


	/**
	 * 执行更新SQL
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	@Transactional
	public Integer update(String sql, List<Object> params) {
		return getSession().update(sql, params);
	}
	/**
	 * 执行插入语句
	 * @param sql
	 * @param params
	 * @return
	 */
	@Transactional
	public Integer insert(String sql, List<Object> params) {
		return getSession().insert(sql, params);
	}
	
	/**
	 * 查询一个对象
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	
	public Integer selectOneCount(String sql, List<Object> params){
		Map<String, Object> result;
		try {
			result = getSqlRunner().selectOne(sql, params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		if(result!=null){
			for(Object obj:result.values()){
				if(obj instanceof Integer||obj instanceof Long){
					return NumberUtils.toInt(obj.toString());
				}
			}
		}
		return 0;
	}
	@Transactional(readOnly = true)
	public Map<String, Object> selectOne(String sql, List<Object> params) {
		try {
			Map<String, Object> result= getSqlRunner().selectOne(sql, params.toArray());
			return this.formatDataColumns(sql, result);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LuException("查询出错:"+e.getMessage());
		}
	}
	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectAll(String sql, List<Object> params) {
		try {
			List<Map<String, Object>> result= getSqlRunner().selectAll(sql, params.toArray());
			return this.formatDataColumns(sql, result);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LuException("查询出错:"+e.getMessage());
		}
	}
	/**
	 * 还原字段大小写
	 * @param sql
	 * @param ltmap
	 * @return
	 */
	private List<Map<String, Object>> formatDataColumns(String sql,List<Map<String, Object>> ltmap){
		List<Map<String, Object>> result = new ArrayList<>();
		if(ltmap==null||ltmap.size()<1){
			return result;
		}
		List<String> ltcolumn=getColumnsName(sql);
		for(Map<String, Object> map:ltmap){
			Map<String, Object> rel = new HashMap<>();
			for(String name:map.keySet()){
				for(String columnName:ltcolumn){
					if(columnName.toLowerCase().equals(name.toLowerCase())){
						rel.put(columnName, map.get(name));
						continue;
					}
				}
			}
			result.add(rel);
		}
		return result;
	}
	private Map<String, Object> formatDataColumns(String sql,Map<String, Object> map){
		Map<String, Object> result = new HashMap<>();
		if(map==null||map.size()<1){
			return result;
		}
		
		List<String> ltcolumn=getColumnsName(sql);
		
		for(String name:map.keySet()){
			for(String columnName:ltcolumn){
				if(columnName.toLowerCase().equals(name.toLowerCase())){
					result.put(columnName, map.get(name));
					continue;
				}
			}
		}
		return result;
	}
	private List<String> getColumnsName(String sql){
		String select=StringUtils.substringBefore(sql, "from");
		select=StringUtils.substringAfter(select, "select");
		String[] columns=StringUtils.split(select, ",");
		List<String> ltcolumn=new ArrayList<>();
		for(String cl:columns ){
			String[] cls=StringUtils.split(cl," ");
			String columnName = cls[cls.length-1];
			if(StringUtils.isBlank(columnName)&&cls.length>1){
				columnName = cls[cls.length-2];
			}
			ltcolumn.add(columnName);
		}
		return ltcolumn;
	}
	/**
	 * 允许直接运行SQL
	 * @return
	 */
	public SqlRunner getSqlRunner(){
		SqlRunner run= new SqlRunner(getSession().getConnection());
		return run;
	}
	
	/**
	 * 获得一个session
	 * 
	 * @return
	 */
	@Transactional
	public SqlSession getSession() {
		try{
				SqlSession session=sessionFactory.getObject().openSession();
			return session;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得session工厂
	 * 
	 * @return
	 */
	@Transactional
	public SqlSessionFactoryBean getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 计算总页数
	 * 
	 * @param count 总计条数
	 * @param rows 每页数量
	 * @return
	 */
	public Integer getPages(Integer count, Integer rows) {
		if (count == null || rows == null) {
			return 1;
		}
		Integer pages = 1;
		if (count % rows == 0) {
			pages = count / rows;
		} else {
			pages = count / rows + 1;
		}
		return pages < 1 ? 1 : pages;
	}
	/**
	 * 将返回的结果集拆分成List<Map<String,Object>> 形式
	 * @author lu
	 * 2015-7-27 下午11:15:13
	 * @param ltobj
	 * @param mof 过滤集合,key 于names的值对应
	 * @param names
	 * @return
	 */
	public List<Map<String, Object>> objToMap(List<Object> ltobj,Map<String, ObjectFilter> mof,String...names){
		List<Map<String, Object>> ltmap = new ArrayList<Map<String,Object>>();
		if(ltobj==null||ltobj.size()<1){
			return ltmap;
		}
		for(int i=0;i<ltobj.size();i++){
			Object[]objs=(Object[])ltobj.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			for(int j=0;j<names.length&&j<objs.length;j++){
				String name=names[j];
				Object obj=objs[j];
				if(mof!=null){
					ObjectFilter of = mof.get(name);
					if(of!=null){
						map.put(name, of.handleOneToString(obj));
					}else{
						map.put(name, obj);
					}
				}else{
					map.put(name, obj);
				}
			}
			if(mof!=null){
				for(ObjectFilter of:mof.values()){
					//处理Map
					of.handleLineMap(map);
				}
			}
			
			ltmap.add(map);
		}
		return ltmap;
	}

	/**
	 * 获得第一条显示的位子
	 * 
	 * @author 路其立 2014-12-13 上午10:31:19
	 * @param page
	 * @param rows
	 * @return
	 */
	public Integer getFrist(Integer page, Integer rows) {
		if(page==null||rows==null){
			return 0;
		}
		Integer f = (page - 1) * rows;
		return f < 0 ? 0 : f;
	}

	public String getLikeString(String key) {
		if(StringUtils.isNotBlank(key)){
			return "%" + key.trim() + "%";
		}else{
			return "%";
		}
	}
	public String getLikeStringLeft(String key) {
		if(StringUtils.isNotBlank(key)){
			return  key.trim()+"%";
		}else{
			return "%";
		}
	}
	public String getMyNameSpace(String method){
		return NameSpace+"."+method;
	}
	
}
