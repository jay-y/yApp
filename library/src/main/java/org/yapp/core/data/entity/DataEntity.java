package org.yapp.core.data.entity;

import android.text.TextUtils;

import org.yapp.db.BaseEntity;
import org.yapp.utils.SignalUtil;
import org.yapp.db.annotation.Column;

/**
 * ClassName: DataEntity <br> 
 * Description: 数据Entity类. <br> 
 * Date: 2015-8-20 上午10:37:29 <br> 
 * 
 * @author ysj 
 * @version 1.0
 * @since JDK 1.7
 */
public abstract class DataEntity<T> extends BaseEntity<T> {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "delFlag")
	public String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	
	@Column(name = "createdTime")
	public long createdTime; // 创建时间
	
	@Column(name = "updatedTime")
	public long updatedTime; // 更新时间
	
	@Column(name = "expires")
	public long expires = Long.MAX_VALUE; // 有效期

	private boolean trust = true;
	
	public DataEntity() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
		long tmpTime = System.currentTimeMillis();
		setCreatedTime(tmpTime);
		setUpdatedTime(tmpTime);
	}
	
	public DataEntity(String id) {
		super(id);
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		setCreatedTime(System.currentTimeMillis());
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		setUpdatedTime(System.currentTimeMillis());
	}
	
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getDelFlag() {
		return delFlag;
	}
	
	/**
	 * @return the createdTime
	 */
	public long getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime the createdTime to set
	 */
	private void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * @return the updateTime
	 */
	public long getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * @param updatedTime the updateTime to set
	 */
	private void setUpdatedTime(long updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	/**
	 * @return the expires
	 */
	public long getExpires() {
		return expires;
	}

	/**
	 * @param expires the expires to set
	 */
	public void setExpires(long expires) {
		this.expires = expires;
	}
	
	/**
	 * isTrust:(是否信任). <br> 
	 * 
	 * @author ysj 
	 * @return 
	 * @since JDK 1.7
	 * date: 2015-12-1 上午9:18:54 <br>
	 */
	public boolean isTrust(){
		if(!TextUtils.isEmpty(this.delFlag) && "0".equals(this.delFlag)){
			// 网络是否畅通
			if(SignalUtil.isNetworkConnected()){
				// 缓存有效期内信任
				if(this.expires > System.currentTimeMillis()){
					trust = true;
				}else{
					trust = false;
				}
			}else{
				trust = true;
			}
		}else{
			trust = false;
		}
		return trust;
	}
}
