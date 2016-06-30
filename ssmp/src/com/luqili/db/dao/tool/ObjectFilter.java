package com.luqili.db.dao.tool;
import java.util.Map;

/**
 * 数据库接口集过滤格式化接口
 * @author lu
 * 2015-7-27 下午11:12:39
 */
public interface ObjectFilter {
	/**
	 * 处理每行传入的指定数据
	 * @param obj
	 * @return
	 */
	public Object handleOneToString(Object obj);
	/**
	 * 处理每行传入的Map(该行数据已加载完成)
	 * @param map
	 */
	public void handleLineMap(Map<String, Object> map);
}
