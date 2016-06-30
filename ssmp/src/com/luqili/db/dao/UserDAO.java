package com.luqili.db.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.luqili.db.beans.base.User;
import com.luqili.tool.page.Page;
@Repository
public class UserDAO extends BaseDAO<User> {
	public void selectUserListPage(Page page){
		String hql_select ="u.id as id,"
				+ "u.add_time as addTime,"
				+ "u.status as status,"
				+ "u.username as username,"
				+ "u.role as role,"
				+ "u.user_right as userRight,"
				+ "ud.name as name,"
				+ "ud.sex as sex,"
				+ "ud.phone as phone,"
				+ "ud.tel as tel,"
				+ "ud.qq as qq,"
				+ "ud.idcard as idcard,"
				+ "ud.address as address,"
				+ "ud.head_img_id as headImgId "
				+ "";
		String hql_from = "base.lu_user u left join base.lu_user_detail ud on u.id=ud.user_id ";
		StringBuffer hql_where = new StringBuffer("");
		String hql_orderby="";
		List<Object> params=null;
		page.resultSql(this, hql_select, hql_from, hql_where, params, hql_orderby);
	}

}
