package com.luqili.db.beans.base;

import java.util.Date;

public class User {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base.lu_user.id
	 * @mbggenerated
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base.lu_user.add_time
	 * @mbggenerated
	 */
	private Date addTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base.lu_user.status
	 * @mbggenerated
	 */
	private Integer status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base.lu_user.username
	 * @mbggenerated
	 */
	private String username;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base.lu_user.password
	 * @mbggenerated
	 */
	private String password;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base.lu_user.pwd_key
	 * @mbggenerated
	 */
	private String pwdKey;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base.lu_user.role
	 * @mbggenerated
	 */
	private Integer role;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column base.lu_user.user_right
	 * @mbggenerated
	 */
	private Long userRight;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base.lu_user.id
	 * @return  the value of base.lu_user.id
	 * @mbggenerated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base.lu_user.id
	 * @param id  the value for base.lu_user.id
	 * @mbggenerated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base.lu_user.add_time
	 * @return  the value of base.lu_user.add_time
	 * @mbggenerated
	 */
	public Date getAddTime() {
		return addTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base.lu_user.add_time
	 * @param addTime  the value for base.lu_user.add_time
	 * @mbggenerated
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base.lu_user.status
	 * @return  the value of base.lu_user.status
	 * @mbggenerated
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base.lu_user.status
	 * @param status  the value for base.lu_user.status
	 * @mbggenerated
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base.lu_user.username
	 * @return  the value of base.lu_user.username
	 * @mbggenerated
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base.lu_user.username
	 * @param username  the value for base.lu_user.username
	 * @mbggenerated
	 */
	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base.lu_user.password
	 * @return  the value of base.lu_user.password
	 * @mbggenerated
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base.lu_user.password
	 * @param password  the value for base.lu_user.password
	 * @mbggenerated
	 */
	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base.lu_user.pwd_key
	 * @return  the value of base.lu_user.pwd_key
	 * @mbggenerated
	 */
	public String getPwdKey() {
		return pwdKey;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base.lu_user.pwd_key
	 * @param pwdKey  the value for base.lu_user.pwd_key
	 * @mbggenerated
	 */
	public void setPwdKey(String pwdKey) {
		this.pwdKey = pwdKey == null ? null : pwdKey.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base.lu_user.role
	 * @return  the value of base.lu_user.role
	 * @mbggenerated
	 */
	public Integer getRole() {
		return role;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base.lu_user.role
	 * @param role  the value for base.lu_user.role
	 * @mbggenerated
	 */
	public void setRole(Integer role) {
		this.role = role;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column base.lu_user.user_right
	 * @return  the value of base.lu_user.user_right
	 * @mbggenerated
	 */
	public Long getUserRight() {
		return userRight;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column base.lu_user.user_right
	 * @param userRight  the value for base.lu_user.user_right
	 * @mbggenerated
	 */
	public void setUserRight(Long userRight) {
		this.userRight = userRight;
	}
}