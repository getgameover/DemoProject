package com.luqili.tool;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author luqili
 * @date 2015年11月25日
 */
public class ParseJsonUtil {

	/**
	 * 将JSON中指定的属性转换为指定类型
	 * 
	 * @param data
	 * @param nodeName
	 * @param _class
	 * @return
	 */
	public static <T> T parseJson(String json, String nodeName, Class<T> _class) {
		T object = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readTree(json); // 读取Json
			JsonNode strs = rootNode.path(nodeName);
			if (!strs.isNull()&&!strs.isMissingNode()) {
				object = mapper.readValue(strs.toString(), _class);
			}
		} catch (Exception e) {
			throw new LuException("JSON解析错误");
		}
		return object;
	}

	/**
	 * 将JSON格式化为指定的对象
	 * 
	 * @param json
	 * @param _class
	 * @return
	 */
	public static <T> T parseJson(String json, Class<T> _class) {
		T object = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			if (json != null && !"".equals(json)) {
				object = mapper.readValue(json, _class);
			}
		} catch (Exception e) {
			throw new LuException("JSON解析错误");
		}
		return object;
	}

	/**
	 * 将JSON格式话为对象，并根据注解进行验证,错误抛出异常
	 * 
	 * @param json
	 * @param _class
	 * @param groups
	 * @return
	 */
	public static <T> T parseJsonValidate(String json, Class<T> _class, Class<?>... groups) {
		T obj = parseJson(json, _class);
		String errors = ValidatorUtil.validate(obj, groups);
		if (StringUtils.isNotBlank(errors)) {
			new LuException(errors);
		}
		return obj;
	}

	/**
	 * 将对象转换为Map
	 * @param obj
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> objectToMap(Object obj) {
		return obj != null ? parseJson(objectToJson(obj), HashMap.class) : null;
	}
	/**
	 * 将对象转为JSON
	 * @param obj
	 * @return
	 */
	public static String objectToJson(Object obj) {
		StringWriter sw = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		if (null != obj) {
			try {
				mapper.writeValue(sw, obj);
			} catch (IOException e) {
				throw new LuException(e.getMessage());
			}
		}
		return sw.toString();
	}
}
