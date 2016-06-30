package com.luqili.tool;

import org.apache.commons.lang3.StringUtils;

/**
 * 检查用户输入工具
 * @author luqili 2016年1月21日
 *
 */
public class CheckInputTool {
	public static boolean checkUserName(String username){
		if(StringUtils.isBlank(username)){
			throw new LuException("账户名不能为空");
		}
		if(username.length()<2||username.length()>30){
			throw new LuException("账户名应为2-20位数字,字母或汉字的字符串!");
		}
		return true;
	}
	public static boolean checkPwd(String pwd){
		if(StringUtils.isBlank(pwd)){
			throw new LuException("密码不能为空");
		}
		if(pwd.length()<6||pwd.length()>30){
			throw new LuException("密码应为6-30位数字或字母混合字符串!");
		}
		return true;
	}
}
