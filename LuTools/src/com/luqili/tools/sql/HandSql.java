package com.luqili.tools.sql;

public class HandSql {
	public static void main(String[]args){
		HandSql hs = new HandSql();
		String sql = "select * from ( " +
                "   select distinct b.id as business_id, " +
                "    s.id as subcategory," +
                "    s.name as subcategory_name," +
                "    c.id as category," +
                "    c.name as category_name," +
                "    gp.name as proc_inst_name," +
                "    u.login_name as operator," +
                "    b.apply_time," +
                "    case when gp.status = 'complete' then '办结' else '未办结' end as status," +
                "    gp.complete_time, " +
                "    view_ac.chname as activ_name, " +
                "    " +
                "    ARRAY_TO_STRING(" +
                "     " +
                "     case when b.subcategory=111001 then" +
                "       array(select distinct tp.location from base.tb_bussiness_resource_relation brr join base.tb_project tp on brr.subject_id = tp.id where brr.table_name='tb_project' and brr.bussiness_id = b.id)" +
                "     " +
                "     when b.subcategory=122100 then" +
                "       array(select distinct concat(street,door_no,name) from measure.tb_building where business_id = b.id)" +
                "     else " +
                "   array(select concat from reg.view_business_house view_bh where view_bh.business_id  =b.id)" +
                "     end " +
                "    ,',') as locations," +
                "    " +
                "    ARRAY_TO_STRING(" +
                "     " +
                "     case when b.subcategory=111001 then" +
                "       array(select distinct tp.name from base.tb_bussiness_resource_relation brr join base.tb_project tp on brr.subject_id = tp.id where brr.table_name='tb_project' and brr.bussiness_id = b.id)" +
                "     when b.subcategory=122100 then" +
                "       array(select distinct project_name from measure.tb_building where business_id = b.id)" +
                "     else " +
                "   array(select project_name from reg.view_business_project view_bp where view_bp.business_id = b.id)" +
                "     end " +
                "    ,',') as project_name," +
                "ARRAY_TO_STRING(" +
                "    " +
                "    case when b.subcategory=152700 then" +
                "    array(" +
                "    select cert.no as cert_no from reg.tb_cert cert where cert.biz_id =b.id" +
                "union all " +
                "select t2.no as cert_no  from reg.tb_business_cert_relation t1 left join reg.tb_cert t2 on t1.cert_id = t2.id where t1.business_id=b.id" +
                "    )" +
                "    else" +
                "    array(select cert.no as cert_no from reg.tb_cert cert where cert.biz_id = b.id)" +
                "    end" +
                " ,',') as cert_no," +
                "    " +
                "    (" +
                "    " +
                "case when c.id=1670 then" +
                " (select o.name from base.tb_org o  right join  base.tb_general_reg_business rb  on rb.id = o.from_biz_id where  rb.id = b.id)" +
                "" +
                "when c.id=1673 then" +
                " ARRAY_TO_STRING(array(select name from base.tb_people where from_biz_id = b.id),',') " +
                "else " +
                "   --view_bp.participants" +
                "" +
                "    (SELECT " +
                "    array_to_string(array_agg((jg.role_name::text || ':'::text) || jg.names), ';'::text) AS participants" +
                "   FROM ( SELECT array_to_string(array_agg(participant.name), ','::text) AS names," +
                "    participant.role_name," +
                "    participant.business_id" +
                "   FROM ( SELECT pr.business_id," +
                "    pr.role_name," +
                "    people.name" +
                "   FROM reg.tb_business_people_relation pr" +
                "     JOIN base.tb_people people " +
                "     ON people.id = pr.people_id AND pr.del_flag = 0 " +
                "     where pr.business_id=b.id" +
                "UNION" +
                " SELECT orgr.business_id," +
                "    orgr.role_name," +
                "    org.name" +
                "   FROM reg.tb_business_org_relation orgr" +
                "     JOIN base.tb_org org ON org.id = orgr.org_id AND orgr.del_flag = 0 " +
                "     where orgr.business_id=b.id) participant" +
                "  GROUP BY participant.role_name, participant.business_id) jg" +
                "  GROUP BY jg.business_id)temp" +
                "  " +
                "end " +
                ") as participants," +
                "    (" +
                "case when b.subcategory=111001 then" +
                "ARRAY_TO_STRING(array(select distinct tp.location from base.tb_bussiness_resource_relation brr join base.tb_project tp on brr.subject_id = tp.id where brr.table_name='tb_project' and brr.bussiness_id = b.id),',') " +
                "when b.subcategory=122100 then" +
                "ARRAY_TO_STRING(array(select distinct concat(street,door_no,name) from measure.tb_building where business_id = b.id),',')" +
                "when (b.subcategory=152700 or b.subcategory=152900 or b.subcategory=163300 or b.subcategory=164100 or b.subcategory=161500) then" +
                "ARRAY_TO_STRING(array(select t2.location from reg.tb_business_house_relation t1 left join base.tb_house t2 on t1.house_id = t2.id where t1.business_id = b.id),',') " +
                "else " +
                "   b.location" +
                "end " +
                ") as location," +
                "    gp.time_limit" +
                "" +
                "    from base.tb_all_biz b inner join base.tb_user u on u.id = b.operator " +
                "    inner join base.tb_people p on u.uuid = p.uuid " +
                "    inner join base.tb_org o on o.id = p.org  " +
                "    inner join base.tb_biz_subcategory s on s.id = b.subcategory" +
                "    inner join base.tb_biz_category c on c.id = TO_NUMBER(substring(b.id from 8 for 4) , '999999')" +
                "    inner join general.tb_proc_inst gp on gp.id = b.process and gp.business = b.id" +
                "    inner join reg.view_procinst_current_active view_ac on view_ac.proc_inst = gp.id" +
                "    left join reg.tb_business_people_relation pr on pr.business_id = b.id" +
                "    left join " +
                "    --left join reg.view_business_participant view_bp on view_bp.business_id = b.id" +
                "     WHERE  b.del_flag = 0 " +
                "    ) a" +
                "      " +
                "     " +
                "       order by apply_time desc ";

		String hsql=HandSql.handSqlSelectFrom(sql);
		System.out.println(sql);
		System.out.println(hsql);
		sql=sql.toLowerCase();
		int scount=positionCountKey(sql, "select ", 0, sql.length());
		int fcount=positionCountKey(sql, " from ", 0, sql.length());
		System.out.println("Select 数量："+scount);
		System.out.println("From 数量："+fcount);
	}
	/**
	 * 将Select 和 From 之间替换成 *
	 * @param sql
	 * @return
	 */
	public static String handSqlSelectFrom(String sql){
		String leftKey="select ";
		String rightKey=" from ";
		String replaceKey="count(1)";
		String handSql=sql;
		int fpt=-1;
		int start=0;
		StringBuffer result=new StringBuffer("");
		while ((fpt=selectFromPosition(handSql, start,leftKey,rightKey))!=-1) {
			int endIndex=fpt+rightKey.length();
			int leftIndex=handSql.indexOf(leftKey);
			result.append(handSql.substring(0,leftIndex+leftKey.length()));
			result.append(replaceKey);
			result.append(handSql.substring(fpt, endIndex));
			handSql=handSql.substring(endIndex);
		}
		result.append(handSql);
		return result.toString();
	}
	/**
	 * 查询与Select匹配的From 位置
	 * @param start 开始查询的位置（在要配置的Select之前）
	 * @return
	 */
	public static int selectFromPosition(String sql,int start,String leftKey,String rightKey){
		sql=sql.toLowerCase();//处理成小写
		int lkpt1=sql.indexOf(leftKey);//第一次出现select的位置
		int rkpt1=sql.indexOf(rightKey);//第一次出现form的位置
		if(lkpt1==-1||rkpt1==-1){
			return -1;
		}
		int lkcount=0;
		while((lkcount=positionCountKey(sql, leftKey, lkpt1+leftKey.length(), rkpt1))!=0){
			lkpt1=rkpt1;
			for(int i=0;i<lkcount;i++){
				int r_start=rkpt1+rightKey.length();
				if(r_start>=sql.length()){
					throw new RuntimeException("SQL书写有误");
				}
				rkpt1=sql.indexOf(rightKey,r_start);
			}
		}
		return rkpt1;
	}
	/**
	 *查询Key出现的次数
	 * @param sql
	 * @param key
	 * @return
	 */
	public static int positionCountKey(String sql,String key,int start,int end){
		if(start>sql.length()||end>sql.length()){
			return -1;
		}
		String s=sql.substring(start, end);
		int count=s.split(key).length-1;//取出第一个分组
		return count;
	}
	
}
