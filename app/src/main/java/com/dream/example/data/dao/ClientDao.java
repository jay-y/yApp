package com.dream.example.data.dao;

import android.database.Cursor;

import com.dream.example.data.entity.Client;

import org.yapp.db.CursorUtils;
import org.yapp.db.Selector;
import org.yapp.db.table.TableEntity;
import org.yapp.ex.DbException;
import org.yapp.utils.IOUtil;
import org.yapp.utils.Log;

/**
 * ClassName: ClientDao <br>
 * Description: 客户端缓存DAO. <br>
 * Date: 2015-8-20 上午10:43:16 <br>
 * 
 * @author user
 * @version
 * @since JDK 1.7
 */
public class ClientDao extends BaseDao<Client> {
	private static final Object lock = new Object();
	private static ClientDao instance;
	
	private ClientDao(){
	}
	
	/**
	 * registerInstance:(获取实例). <br>
	 * 
	 * @author ysj
	 * @since JDK 1.7 date: 2015-12-3 上午11:41:02 <br>
	 */
	public static ClientDao getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new ClientDao();
				}
			}
		}
		return instance;
	}
	
	@Override
	public void save(Client entity) {
		if(entity.getId()!=null){
			entity.preUpdate();
			this.update(entity);
		}else{
			entity.preInsert();
			this.add(entity);
		}
	}
	
	/**
	 * get:(获取Client). <br> 
	 * 
	 * @author ysj 
	 * @return 
	 * @since JDK 1.7
	 * date: 2015-12-8 上午11:02:57 <br>
	 */
	public Client get() {
		try {
			return dbManager.findFirst(Client.class);
		} catch (DbException e) {
			Log.e(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * findByUserName:(根据username查询数据). <br> 
	 * 
	 * @author ysj 
	 * @param username
	 * @return
	 * @throws DbException
	 * @since JDK 1.7
	 * date: 2015-11-30 下午5:44:23 <br>
	 */
	public Client findByUserName(String username){
		try {
			TableEntity<Client> table = dbManager.getTable(Client.class);
			Selector<Client> selector = dbManager.selector(Client.class).where("username", "=", username);
			String sql = selector.limit(1).toString();
			Cursor cursor = dbManager.execQuery(sql);
			if (cursor != null) {
				try {
					if (cursor.moveToNext()) {
						return CursorUtils.getEntity(table, cursor);
					}
				} catch (Throwable e) {
					throw new DbException(e);
				} finally {
					IOUtil.closeQuietly(cursor);
				}
			}
		} catch (DbException e) {
			Log.e(e.getMessage(), e);
			return null;
		}
		return null;
	}
}
