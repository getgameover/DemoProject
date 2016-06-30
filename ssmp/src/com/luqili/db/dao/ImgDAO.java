package com.luqili.db.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luqili.db.beans.base.Img;
import com.luqili.service.ImgService;
import com.luqili.tool.page.Page;
@Repository
public class ImgDAO extends BaseDAO<Img> {
	@Autowired
	private ImgService imgService;
	
	public void selectImgListPage(Page page,Integer userid){
		String hql_select ="m.id as id,"
				+ "m.add_time as addTime,"
				+ "m.status as status,"
				+ "m.name as name,"
				+ "m.filename as filename,"
				+ "m.size as size,"
				+ "m.img_type as imgType,"
				+ "m.add_user_id as addUserId,"
				+ "m.url as url, "
				+ "m.ext as ext  "
				+ "";
		String hql_from = "base.lu_img m ";
		StringBuffer hql_where = new StringBuffer("m.add_user_id = ?");
		String hql_orderby="";
		List<Object> params=new ArrayList<>();
		params.add(userid);
		page.resultSql(this, hql_select, hql_from, hql_where, params, hql_orderby);
		if(page.getData()!=null){
			for(Map<String, Object> obj:page.getData()){
				imgService.formatImgUrlByMap(obj);
			}
		}
	}
}
