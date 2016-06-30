package com.luqili.power;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MenuData {
	//手风琴事件
	MenuCommon(RightData.Common,1,"我的信息"),
	//其他事件
	MenuCommon_MyIndex(RightData.Common,1,"我的主页","navTab","main","my_index.html","load_myindex",MenuData.MenuCommon),
	//文件夹事件
	MenuCommon_MyMsg(RightData.Common,1,"个人信息",MenuData.MenuCommon),
	//click事件
	MenuCommon_MyMsg_MSg(RightData.Common,1,"用户信息","lu.upUserMsg();",MenuData.MenuCommon_MyMsg),
	//其他事件 文件管理
	MenuCommon_MyMsg_ManageFiles(RightData.Common,1,"文件列表","navTab","common-mymsg-managefiles","common/MamageFilesList.html","comOpenFilesList",MenuData.MenuCommon_MyMsg),
//	MenuCommon_MyMsg_ChangePwd(RightData.Common,1,"修改密码","dialog","common-mymsg-changepwd","common/ChangePwd.html","changepwd",340,220,MenuData.MenuCommon_MyMsg),
	//click事件
	MenuCommon_MyMsg_ChangePwd(RightData.Common,1,"修改密码","lu.changepwd();",MenuData.MenuCommon_MyMsg),
	MenuSystem(RightData.System,2,"系统管理"),
	MenuSystem_Msg(RightData.System,1,"用户管理","navTab","system-msg-userlist","system/ManageUserLt.html","sysManageUserLtInit",MenuData.MenuSystem),
	;
	private MenuData parent;
	private RightData right;
	private int order;
	private String target;
	private String rel;
	private String name;
	private String href;
	private String loaded;//加载成功后执行的方法
	private String click;//点击触发事件
	private Integer width;
	private Integer height;
	//仅限于手风琴组件的Header信息
	MenuData(RightData right,int order,String name){
		this.right=right;
		this.order=order;
		this.target="accordionHeader";
		this.name=name;
	}
	//文件夹使用
	MenuData(RightData right,int order,String name,MenuData parent){
		this.right=right;
		this.order=order;
		this.name=name;
		this.parent=parent;
		this.target="";
		
	}
	//click事件使用
	MenuData(RightData right,int order,String name,String click,MenuData parent){
		this.right=right;
		this.order=order;
		this.target="";
		this.name=name;
		this.click=click;
		this.href="javascript:;";
		this.parent=parent;
	}
	//navTab 使用
	MenuData(RightData right,int order,String name,String target,String rel,String href,String loaded,MenuData parent){
		this.right=right;
		this.order=order;
		this.target=target;
		this.rel=rel;
		this.name=name;
		this.href=href;
		this.loaded=loaded;
		this.parent=parent;
		
	}
	//dialog 使用
	MenuData(RightData right,int order,String name,String target,String rel,String href,String loaded,Integer width,Integer height,MenuData parent){
		this.right=right;
		this.order=order;
		this.target=target;
		this.name=name;
		this.href=href;
		this.loaded=loaded;
		this.width=width;
		this.height=height;
		this.parent=parent;
	}
	/**
	 * 获得该权限下的菜单列表
	 * @param right
	 * @return
	 */
	public static List<Map<String, Object>> getListMenusMsgByRight(long right){
		List<MenuData> menus=getMenusByRight(right);
		return getFormatParents(menus, null);
	}
	private static List<Map<String, Object>> getFormatParents(List<MenuData> menus,MenuData parent){
		List<MenuData> ltparents = getMenusByParent(menus, parent);
		if(ltparents!=null&&ltparents.size()>0){
			List<Map<String, Object>> ltmap = new ArrayList<>();
			for(MenuData p:ltparents){
				Map<String, Object> map = new HashMap<>();
				map.put("menu", p.toMap());
				List<Map<String, Object>> ltchild = getFormatParents(menus, p);
				if(ltchild!=null){
					map.put("menus", ltchild);
				}
				ltmap.add(map);
			}
			return ltmap;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 获得指定权限下的菜单
	 * @param right
	 * @return
	 */
	public static List<MenuData> getMenusByRight(RightData right){
		return getMenusByRight(right.getRight());
	}
	/**
	 * 转化为Map
	 * @return
	 */
	public Map<String, Object> toMap(){
		Map<String, Object> map = new HashMap<>();
		map.put("target", this.target);
		map.put("rel", this.rel);
		map.put("name", this.name);
		map.put("href", this.href);
		map.put("loaded", this.loaded);
		map.put("width", this.width);
		map.put("height", this.height);
		map.put("click", this.click);
		return map;
	}
	/**
	 * 查询该权限下的所有菜单
	 * @param right
	 * @return
	 */
	public static List<MenuData> getMenusByRight(long right){
		MenuData[] datas=MenuData.values();
		List<MenuData> ltdata=new ArrayList<>();
		for(MenuData m:datas){
			if((m.getRight().getRight()&right)==m.getRight().getRight()){
				ltdata.add(m);
			}
		}
		ltdata.sort(new Comparator<MenuData>() {
			@Override
			public int compare(MenuData arg0, MenuData arg1) {
				return arg0.getOrder()-arg1.getOrder();
			}
		});
		return ltdata;
	}
	/**
	 * 在指定数据中获取父类下在子类
	 * @param datas
	 * @param parent
	 * @return
	 */
	public static List<MenuData> getMenusByParent(List<MenuData> datas,MenuData parent){
		List<MenuData> ltdata=new ArrayList<>();
		for(MenuData m:datas){
			if(m.getParent()==parent){
				ltdata.add(m);
			}
		}
		ltdata.sort(new Comparator<MenuData>() {
			@Override
			public int compare(MenuData arg0, MenuData arg1) {
				return arg0.getOrder()-arg1.getOrder();
			}
		});
		return ltdata;
	}
	
	public MenuData getParent() {
		return parent;
	}
	public void setParent(MenuData parent) {
		this.parent = parent;
	}
	public RightData getRight() {
		return right;
	}
	public void setRight(RightData right) {
		this.right = right;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClick() {
		return click;
	}
	public void setClick(String click) {
		this.click = click;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getLoaded() {
		return loaded;
	}
	public void setLoaded(String loaded) {
		this.loaded = loaded;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	
}
