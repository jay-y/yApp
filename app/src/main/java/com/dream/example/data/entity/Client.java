package com.dream.example.data.entity;

import org.yapp.core.data.entity.DataEntity;
import org.yapp.db.annotation.Column;
import org.yapp.db.annotation.Table;

/**
 * ClassName: Client <br>
 * Description: 客户端信息. <br>
 * Date: 2015-8-21 上午10:49:39 <br> 
 * 
 * @author ysj 
 * @version  1.0
 * @since JDK 1.7
 */
@Table(name = "card_client")
public class Client extends DataEntity<Client> {
	/** 
	 * @since JDK 1.7
	 */ 
	private static final long serialVersionUID = 1L;
	
	@Column(name = "sessionId")
	private String sessionId;
	
	@Column(name = "userId")
	private String userId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "versionNo")
	private String versionNo;
	
	@Column(name = "location")
	private String location; // 详细地址
	
	@Column(name = "province")
	private String province; // 省
	
	@Column(name = "city")
	private String city; // 市
	
	@Column(name = "distinct")
	private String distinct; // 区
	
	@Column(name = "locLongitude")
	private double locLongitude; // 经度
	
	@Column(name = "locLatitude")
	private double locLatitude; // 纬度

	private String userInfo = null;
	
	private int status = 0; // 客户端状态 0:未登录 1:已登录 2:未签到 3:已签到

	public Client(String id){
		super(id);
		setExpires(System.currentTimeMillis() + (1800 * 1000));
	}
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the locLongitude
	 */
	public double getLocLongitude() {
		return locLongitude;
	}

	/**
	 * @param locLongitude the locLongitude to set
	 */
	public void setLocLongitude(double locLongitude) {
		this.locLongitude = locLongitude;
	}

	/**
	 * @return the locLatitude
	 */
	public double getLocLatitude() {
		return locLatitude;
	}

	/**
	 * @param locLatitude the locLatitude to set
	 */
	public void setLocLatitude(double locLatitude) {
		this.locLatitude = locLatitude;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the distinct
	 */
	public String getDistinct() {
		return distinct;
	}

	/**
	 * @param distinct the distinct to set
	 */
	public void setDistinct(String distinct) {
		this.distinct = distinct;
	}

	/**
	 * @return the userInfo
	 */
	public String getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
}
