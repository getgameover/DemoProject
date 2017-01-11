package com.luqili.tool.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luqili.db.dao.BaseDAO;
import com.luqili.tool.LuException;
import com.luqili.tool.ParseJsonUtil;

/**
 * 
 * @author luqili 2016年1月22日
 *
 */
public class Page {
	private static Logger log = LogManager.getLogger(Page.class);
	private Integer draw;//请求次数
    private int start = 0;//起始位置
    private int length = 15;//每页的条数
    private Search search;//搜索条件
    private List<Order> order;
    private List<Column> columns;
    private int recordsTotal;//总记录数   
    private int recordsFiltered;//过滤后总记录数   
    private List<Map<String, Object>> data;//对应的当前页记录  
    private String error;
    
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }

	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	public List<Order> getOrder() {
		return order;
	}
	public void setOrder(List<Order> order) {
		this.order = order;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public int getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public int getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	@Override  
    public String toString() {  
       StringBuilder tos = new StringBuilder();  
       tos.append("Page [start=").append(start).append(", pageSize=")  
              .append(length).append(", results=").append(data).append(  
                     ", totalRecord=").append(recordsTotal).append("]");  
       return tos.toString();  
    }
	
	/**
     * 根据前台参数自动生成排序字符串
     * @param page
     * @param columns
     * @param order
     */
	public static Page createPage(String jsonString){
		Page page = (Page) ParseJsonUtil.parseJson(jsonString, Page.class);
		return page;
	}
	
	
	/**
	 * 查询分页结果
	 * @param baseDAO
	 * 	不能包含SQL的关键词
	 * @param sql_select
	 * @param sql_from
	 * @param sql_where
	 * @param sql_orderby
	 */
	public void resultSql(BaseDAO<?> baseDAO,String sql_select,String sql_from,StringBuffer sql_where,List<Object> params, String sql_orderby){
		List<Column> columns = this.getColumns();
		if(sql_where==null){
			sql_where = new StringBuffer();
		}
		if(params == null){
			params =new ArrayList<>();
		}
		if(columns!=null&&columns.size()>0){
			StringBuffer search = new StringBuffer();//需要搜索的条件
			boolean isor=false;//是否添加or
			List<Object> _params = new ArrayList<>();
			for(Column cum:columns){
				if(StringUtils.isNotBlank(cum.getName())&&cum.getSearch()!=null
						&&cum.getSearch().getValue()!=null
						&&StringUtils.isNotBlank(cum.getSearch().getValue().toString())){
					Search sh = cum.getSearch();
					if(sh==null||StringUtils.isBlank(sh.getValue())||StringUtils.isBlank(sh.getType())){
						continue;
					}
					if(isor){
						search.append(" or ");
					}
					search.append(cum.getName());
					switch (sh.getType()) {
					case "String":
						search.append(" like ? ");
						_params.add(baseDAO.getLikeString(sh.getValue().toString()));
						break;
					case "Integer":
						search.append(" = ? ");
						_params.add(NumberUtils.toInt(sh.getValue()));
						break;
					case "Long":
						search.append(" = ? ");
						_params.add(NumberUtils.toLong(sh.getValue()));
						break;
					default:
						throw new LuException("不支持的数据类型:"+sh.getType());
					}
					isor=true;
				}
				
			}
			if(StringUtils.isNotBlank(search)){
				search.insert(0, "(");
				search.append(")");
			}
			if(StringUtils.isNotBlank(search)){
				sql_where.append(" and ");
			}
			sql_where.append(search);
			params.addAll(_params);
		}
		
		StringBuffer data_sql=new StringBuffer();
		StringBuffer count_sql=new StringBuffer();
		if(StringUtils.isNotBlank(sql_select)){
			data_sql.append("select ");
			data_sql.append(sql_select);
			data_sql.append(" ");
		}
		data_sql.append(" from ");
		data_sql.append(sql_from);
		
		count_sql.append("select count(*) from ");
		count_sql.append(sql_from);
		
		if(StringUtils.isNotBlank(sql_where)){
			data_sql.append(" where ");
			data_sql.append(sql_where);
			
			count_sql.append(" where ");
			count_sql.append(sql_where);
		}
		
		StringBuffer orderString = new StringBuffer();
		for(Order order:this.getOrder()){
			Integer postion = order.getColumn();
			String dir = order.getDir();
			Column column = columns.get(postion);
			String name = column.getName();
			boolean isdh=false;
			if(StringUtils.isNotBlank(name)){
				if(isdh){
					orderString.append(",");
				}
				orderString.append(name);
				orderString.append(" ");
				orderString.append(dir);
				isdh=true;
			}
		}
		if(StringUtils.isNotBlank(orderString)){
			if(StringUtils.isNotBlank(sql_orderby)){
				orderString.append(",");
				orderString.append(sql_orderby);
			}else{
				orderString=orderString.append(sql_orderby);
			}
			
		}
		if(StringUtils.isNotBlank(orderString)){
			data_sql.append(" order by ");
			data_sql.append(orderString);
		}
		data_sql.append(" limit ? offset ?");
		List<Object> data_params=new ArrayList<>(params);
		data_params.add(this.getLength());
		data_params.add(this.getStart());
		
		log.debug("data_sql:"+data_sql.toString());
		log.debug("data_params:"+data_params.toString());
		log.debug("count_sql:"+count_sql.toString());
		log.debug("params:"+params.toString());
		
		List<Map<String, Object>> ltdata = baseDAO.selectAll(data_sql.toString(),data_params);
		this.setData(ltdata);
		Integer count=baseDAO.selectOneCount(count_sql.toString(), params);
		this.setRecordsTotal(count);
		this.setRecordsFiltered(count);
	}
}
