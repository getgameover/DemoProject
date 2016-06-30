package com.luqili.tool;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * 注解验证工具
 * @author luqili
 * @date 2015年11月25日
 */
public class ValidatorUtil {
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	/**
	 * 验证该Object 中是否含有错误数据，没有错误返回 “”
	 * @param obj
	 * @return
	 */
	public static String validate(Object obj,Class<?> ...groups){
		if(obj==null){
			return "Object is null !";
		}
		Set<ConstraintViolation<Object>> errors=validator.validate(obj,groups);
		Iterator<ConstraintViolation<Object>> iterator=errors.iterator();
		StringBuffer sbf_errors=new StringBuffer();
		while(iterator.hasNext()){
			sbf_errors.append(iterator.next().getMessage());
			if(iterator.hasNext()){
				sbf_errors.append("\r\n");
			}
		}
		return sbf_errors.toString();
	}
	/**
	 * 返回验证到的第一个错误，没有错误返回 “”
	 * @param obj
	 * @return
	 */
	public static String validateFirstError(Object obj,Class<?> ...groups){
		if(obj==null){
			return "Object is null !";
		}
		Set<ConstraintViolation<Object>> errors=validator.validate(obj,groups);
		Iterator<ConstraintViolation<Object>> iterator=errors.iterator();
		if(iterator.hasNext()){
			return iterator.next().getMessage();
		}
		return "";
	}
	/**
	 * 该Object是否通过验证
	 * @param obj
	 * @return
	 */
	public static boolean validateIsOk(Object obj,Class<?> ...groups){
		return obj==null?false:validator.validate(obj,groups).isEmpty();
	}
	/**
	 * 验证指定属性是否含有错误数据，没有错误返回 “”
	 * @param obj
	 * @param propertyName
	 * @return
	 */
	public static String validateProperty(Object obj,String propertyName,Class<?> ...groups){
		if(obj==null){
			return "Object is null !";
		}
		Set<ConstraintViolation<Object>> errors=validator.validateProperty(obj, propertyName,groups);
		Iterator<ConstraintViolation<Object>> iterator=errors.iterator();
		StringBuffer sbf_errors=new StringBuffer();
		while(iterator.hasNext()){
			sbf_errors.append(iterator.next().getMessage());
			if(iterator.hasNext()){
				sbf_errors.append("\r\n");
			}
		}
		return sbf_errors.toString();
	}
	/**
	 * 指定属性遇到的第一个错误
	 * @param obj
	 * @param propertyName
	 * @param groups
	 * @return
	 */
	public static String validatePropertyFirst(Object obj,String propertyName,Class<?> ...groups){
		if(obj==null){
			return "Object is null !";
		}
		Set<ConstraintViolation<Object>> errors=validator.validateProperty(obj, propertyName,groups);
		Iterator<ConstraintViolation<Object>> iterator=errors.iterator();
		return iterator.hasNext()?iterator.next().getMessage():"";
	}
	/**
	 * 指定属性是否通过验证
	 * @param obj
	 * @param propertyName
	 * @param groups
	 * @return
	 */
	public static boolean validatePropertyIsOk(Object obj,String propertyName,Class<?> ...groups){
		return obj==null?false:validator.validateProperty(obj,propertyName,groups).isEmpty();
	}
	
	
}
