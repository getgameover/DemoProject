package com.luqili.tool.enums;

public enum Role {
	Client(0,"游客"),Admin(999,"管理员");
	private String roleName;
	private Integer roleValue;
	Role(Integer roleValue,String roleName){
		this.roleName=roleName;
		this.roleValue=roleValue;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getRoleValue() {
		return roleValue;
	}
	public void setRoleValue(Integer roleValue) {
		this.roleValue = roleValue;
	}
	
}
