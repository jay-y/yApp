package org.yapp.core.data.abase;

import java.util.List;

/** 
 * ClassName: AppDBUtil <br> 
 * Description: 数据库工具类接口. <br> 
 * Date: 2015-8-20 上午10:18:52 <br> 
 * 
 * @author ysj 
 * @version  1.0
 * @since JDK 1.7
 */
public interface BaseDbApi<T> {
	/** 
	 * 数据库 添加数据
	 * 
	 * @param entity 实体
	 */
	void add(T entity); 
	
	/** 
	 * 数据库 更新数据
	 * 
	 * @param entity 实体
	 */
	void update(T entity); 
	
	/** 
	 * save:(保存数据). <br> 
	 * 
	 * @author ysj 
	 * @param entity 
	 * @since JDK 1.7
	 * date: 2015-12-8 上午10:28:31 <br> 
	 */  
	void save(T entity);
	
	/**
	 * 数据库 删除数据（单条）
	 * 
	 * @param entity
	 */
	void delete(T entity); 
	
	/**
	 * 数据库 查询数据
	 * 
	 * @param classType 对象类型
	 */
	abstract List<T> query(Class<T> classType);
}
