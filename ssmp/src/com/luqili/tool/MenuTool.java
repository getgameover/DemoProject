package com.luqili.tool;

import java.util.List;
import java.util.Map;

import com.luqili.power.MenuData;
/**
 * 菜单工具类
 * @author 46155
 *
 */
public class MenuTool {
	public static List<Map<String, Object>> getMenus(long right){
		return MenuData.getListMenusMsgByRight(right);
	}
}
