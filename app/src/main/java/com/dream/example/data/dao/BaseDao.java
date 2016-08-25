package com.dream.example.data.dao;

import com.dream.example.data.support.AppConsts;

import org.yapp.DbManager;
import org.yapp.core.data.abase.BaseDbApi;
import org.yapp.ex.DbException;
import org.yapp.utils.Log;
import org.yapp.y;

import java.util.List;

/**
 * ClassName: BaseDao <br>
 * Description: DAO基类. <br>
 * Date: 2015-8-20 上午10:21:04 <br>
 * 
 * @author ysj
 * @version 1.0
 * @since JDK 1.7
 */
public abstract class BaseDao<T> implements BaseDbApi<T> {
	protected static DbManager dbManager = y.getDb(AppConsts.DbConfig.ClientDb);

	/**
	 * 创建数据库
	 * 
	 * @param db_name 数据库名
	 */
	public void initDB(String db_name) {
		dbManager.getDaoConfig().setDbName(db_name);
	}

	@Override
	public void add(T entity) {
		try {
			dbManager.save(entity);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(T entity){
		try {
			dbManager.saveOrUpdate(entity);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(T entity) {
		try {
			dbManager.delete(entity);
		} catch (DbException e) {
			e.printStackTrace();
			Log.e(dbManager.getDaoConfig().getDbName() + ":数据库无数据!");
		}
	}
	
	@Override
	public List<T> query(Class<T> classType){
		try {
			return dbManager.findAll(classType);
		} catch (DbException e) {
			Log.e(e.getMessage(), e);
			return null;
		}
	}
	
	@Override
	public abstract void save(T entity);
}
